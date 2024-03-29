package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.interfaces.storage.FilmStorage;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

import static ru.yandex.practicum.filmorate.message.FilmLogMessage.WARN_ADD_GENRE;
import static ru.yandex.practicum.filmorate.sql.FilmSql.*;

@Slf4j
@Repository
public class FilmRepository extends AbstractStorage<Film> implements FilmStorage {
    @Autowired
    public FilmRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    private void incrementRate(long filmId) {
        super.update(INCREMENT_RATE_SQL.getSql(), filmId);
    }

    private void decrementRate(long filmId, long userID) {
        super.update(
                DECREMENT_RATE_SQL.getSql(),
                filmId,
                userID);
    }

    private void updateFilmInDb(Film film) {
        super.update(
                UPDATE_SQL.getSql(),
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRate(),
                film.getId());
    }

    private void updateGenreInDb(Film film) {
        super.update(GENRE_DELETE_SQL.getSql(), film.getId());
        this.addGenreToDb(film);
    }

    private void putMpaToDb(Film film, String sql) {
        super.update(
                sql,
                film.getMpa().getId(),
                film.getId());
    }

    private void addGenreToDb(Film film) {
        try {
            List<Genre> genres = new ArrayList<>(film.getGenres());
            jdbcTemplate.batchUpdate(
                    GENRE_ADD_SQL.getSql(),
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            long filmId = film.getId();
                            int genreId = genres.get(i).getId();
                            ps.setLong(1, filmId);
                            ps.setLong(2, genreId);
                        }

                        @Override
                        public int getBatchSize() {
                            return genres.size();
                        }
                    });
        } catch (DuplicateKeyException e) {
            log.warn(WARN_ADD_GENRE.message(), film.getId(), film.getGenres());
        }
    }

    @Override
    public Film add(Film film) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(ADD_SQL.getSql(), new String[]{"ID"});
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setDate(3, Date.valueOf(film.getReleaseDate()));
            ps.setInt(4, film.getDuration());
            ps.setInt(5, film.getRate());
            return ps;
        }, keyHolder);
        film.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        if (film.getMpa() != null) {
            this.putMpaToDb(film, MPA_ADD_SQL.getSql());
        }
        if (film.getGenres() != null) {
            this.addGenreToDb(film);
        }
        return super.get(
                SQL_RECEIVE_BY_ID.getSql(),
                new FilmMapper(),
                film.getId());
    }

    @Override
    public void addLike(long filmId, long userId) {
        super.add(
                LIKE_ADD_SQL.getSql(),
                filmId,
                userId);
        incrementRate(filmId);
    }

    @Override
    public void removeLike(long id, long userId) {
        super.delete(
                LIKE_DELETE_SQL.getSql(),
                id,
                userId);
        this.decrementRate(id, userId);
    }

    @Override
    public Film update(Film film) {
        this.updateFilmInDb(film);
        this.putMpaToDb(film, MPA_UPDATE_SQL.getSql());
        this.updateGenreInDb(film);
        return super.get(
                SQL_RECEIVE_BY_ID.getSql(),
                new FilmMapper(),
                film.getId());
    }

    public Film get(Object... args) {
        return super.get(SQL_RECEIVE_BY_ID.getSql(), new FilmMapper(), args);
    }

    @Override
    public Collection<Film> getList() {
        return super.getList(SQL_RECEIVE_LIST.getSql(), new FilmMapper());
    }

    @Override
    public Collection<Film> getPopularList(int count) {
        return super.getList(SQL_RECEIVE_POPULAR_FILMS.getSql(), new FilmMapper(), count);
    }
}