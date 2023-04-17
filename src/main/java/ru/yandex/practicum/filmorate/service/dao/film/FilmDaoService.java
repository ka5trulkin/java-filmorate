package ru.yandex.practicum.filmorate.service.dao.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.film.BadFilmLikeException;
import ru.yandex.practicum.filmorate.exception.film.FilmLikeAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.film.FilmLikeNotFoundException;
import ru.yandex.practicum.filmorate.exception.object.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.film.FilmDb;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.model.film.Mpa;
import ru.yandex.practicum.filmorate.service.dao.AbstractDao;
import ru.yandex.practicum.filmorate.service.interfaces.FilmDao;

import java.util.List;

import static ru.yandex.practicum.filmorate.message.FilmLogMessage.*;
import static ru.yandex.practicum.filmorate.service.dao.film.FilmSql.*;

@Slf4j
@Service
public class FilmDaoService extends AbstractDao<FilmDb> implements FilmDao<FilmDb> {
    private static final String TABLE_NAME = "FILM_DB";

    @Autowired
    protected FilmDaoService(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    private void addFilmToDb(FilmDb film) {
        jdbcTemplate.update(
                FILM_ADD_SQL.getSql(),
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration());
    }

    private void updateFilmInDb(FilmDb film) {
        jdbcTemplate.update(
                FILM_UPDATE_SQL.getSql(),
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getId());
    }

    private void addGenreToDb(FilmDb film, long filmId) {
        try {
            film.getGenres().forEach(
                    genre -> jdbcTemplate.update(GENRE_ADD_SQL.getSql(),
                            filmId,
                            genre.getId()));
        } catch (DuplicateKeyException e) {
            log.warn(WARN_ADD_GENRE.message(), filmId, film.getGenres());
        }
    }

    private void updateGenreInFilm(FilmDb film) {
        jdbcTemplate.update(GENRE_DELETE_SQL.getSql(), film.getId());
        addGenreToDb(film, film.getId());
    }

    private void putRateToDb(FilmDb film, String sql, long filmId) {
        jdbcTemplate.update(
                sql,
                film.getRate(),
                filmId);
    }

    private void putMpaToDb(FilmDb film, String sql, long filmId) {
        jdbcTemplate.update(sql,
                film.getMpa().getId(),
                filmId);
    }

    private void incrementRate(long filmId) {
        jdbcTemplate.update(
                INCREMENT_RATE_SQL.getSql(),
                filmId);
    }

    private void decrementRate(long filmId, long userID) {
        jdbcTemplate.update(DECREMENT_RATE_SQL.getSql(), filmId, userID);
    }

    private void checkIsLiked(long filmId, long userId) {
        try {
            jdbcTemplate.queryForObject(
                    CHECK_IS_LIKED.getSql(),
                    Boolean.class, filmId, userId);
        } catch (EmptyResultDataAccessException e) {
            throw new FilmLikeNotFoundException(filmId, userId);
        }
    }

    @Override
    public FilmDb add(FilmDb film) {
        this.addFilmToDb(film);
        long filmId = super.getLastIdFromDataBase(TABLE_NAME);
        this.putRateToDb(film, RATE_ADD_SQL.getSql(), filmId);
        if (film.getMpa() != null) {
            this.putMpaToDb(film, MPA_ADD_SQL.getSql(), filmId);
        }
        if (film.getGenres().size() > 0) {
            this.addGenreToDb(film, filmId);
        }
        log.info(FILM_ADDED.message(), film.getName());
        return super.get(SQL_RECEIVE_FILM_BY_ID.getSql(), new FilmMapper(), filmId);
    }

    @Override
    public FilmDb update(FilmDb film) {
        this.updateFilmInDb(film);
        this.putRateToDb(film, RATE_UPDATE_SQL.getSql(), film.getId());
        this.putMpaToDb(film, MPA_UPDATE_SQL.getSql(), film.getId());
        this.updateGenreInFilm(film);
        log.info(FILM_UPDATED.message(), film.getId(), film.getName());
        return super.get(SQL_RECEIVE_FILM_BY_ID.getSql(), new FilmMapper(), film.getId());
    }

    @Override
    public FilmDb get(long id) {
        log.info(GET_FILM.message(), id);
        return super.get(SQL_RECEIVE_FILM_BY_ID.getSql(), new FilmMapper(), id);
    }

    @Override
    public List<FilmDb> getList() {
        log.info(GET_FILM_LIST.message());
        return super.getList(SQL_RECEIVE_FILM_LIST.getSql(), new FilmMapper());
    }

    @Override
    public void addLike(long id, long userId) {
        try {
            jdbcTemplate.update(
                    LIKE_ADD_SQL.getSql(),
                    id,
                    userId);
            this.incrementRate(id);
            log.info(FILM_LIKE_ADDED.message(), id, userId);
        } catch (DuplicateKeyException e) {
            throw new FilmLikeAlreadyExistException(id, userId);
        } catch (DataIntegrityViolationException e) {
            throw new BadFilmLikeException(id, userId);
        }
    }

    @Override
    public void removeLike(long id, long userId) {
        this.checkIsLiked(id, userId);
        this.decrementRate(id, userId);
        try {
            jdbcTemplate.update(
                    LIKE_DELETE_SQL.getSql(),
                    id,
                    userId);
            log.info(FILM_LIKE_REMOVED.message(), id, userId);
        } catch (DuplicateKeyException e) {
            throw new FilmLikeAlreadyExistException(id, userId);
        } catch (DataIntegrityViolationException e) {
            throw new BadFilmLikeException(id, userId);
        }
    }

    @Override
    public List<FilmDb> getPopularList(long count) {
        log.info(GET_POPULAR_FILM_LIST.message());
        return super.getList(
                String.format(SQL_RECEIVE_POPULAR_FILMS.getSql(), count),
                new FilmMapper());
    }

    @Override
    public List<Genre> getGenreList() {
        log.info(GET_GENRE_LIST.message());
        return jdbcTemplate.query("SELECT ID, NAME FROM GENRE",
                new BeanPropertyRowMapper<>(Genre.class));
    }

    @Override
    public Genre getGenreById(short id) {
        try {
            log.info(GET_GENRE.message(), id);
            return jdbcTemplate.queryForObject(String.format("SELECT ID, NAME FROM GENRE WHERE ID = %d", id), new BeanPropertyRowMapper<>(Genre.class));
        } catch (EmptyResultDataAccessException e) {
            throw new ObjectNotFoundException(id);
        }
    }

    @Override
    public List<Mpa> getMpaList() {
        log.info(GET_MPA_LIST.message());
        return jdbcTemplate.query("SELECT ID, NAME FROM MPA",
                new BeanPropertyRowMapper<>(Mpa.class));
    }

    @Override
    public Mpa getMpaById(short id) {
        try {
            log.info(GET_MPA.message(), id);
            return jdbcTemplate.queryForObject(String.format("SELECT ID, NAME FROM MPA WHERE ID = %d", id), new BeanPropertyRowMapper<>(Mpa.class));
        } catch (EmptyResultDataAccessException e) {
            throw new ObjectNotFoundException(id);
        }
    }
}