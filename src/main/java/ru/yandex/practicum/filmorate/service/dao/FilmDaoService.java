package ru.yandex.practicum.filmorate.service.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.film.FilmDb;
import ru.yandex.practicum.filmorate.service.interfaces.FilmDao;

import java.util.List;

import static ru.yandex.practicum.filmorate.message.FilmLogMessage.*;

@Slf4j
@Service
public class FilmDaoService extends AbstractDao<FilmDb> implements FilmDao<FilmDb> {
    private final String tableName = "film";
    private final String sqlReceiveFilmData = "SELECT " +
            "f.ID, f.NAME, f.DESCRIPTION, f.RELEASE_DATE, f.DURATION, " +
            "r.RATE rate, " +
            "m.ID mpaId, m.NAME mpaName, " +
            "g.ID genreId, g.NAME genreName " +
            "FROM FILM f " +
            "LEFT JOIN FILM_GENRE fg ON f.ID = fg.FILM_ID " +
            "LEFT JOIN GENRE g ON fg.GENRE_ID = g.ID " +
            "LEFT JOIN FILM_MPA fm ON fm.FILM_ID = f.ID " +
            "LEFT JOIN MPA m ON fm.MPA_ID = m.ID " +
            "LEFT JOIN RATE r ON f.ID = r.FILM_ID";
    private final String filmAddSql = "INSERT INTO FILM(NAME, DESCRIPTION, RELEASE_DATE, DURATION) VALUES(?, ?, ?, ?)";
    private final String rateAddSql = "INSERT INTO RATE (RATE, FILM_ID) VALUES(?, ?)";
    private final String mpaAddSql = "INSERT INTO FILM_MPA (MPA_ID, FILM_ID) VALUES(?, ?)";
    private final String genreAddSql = "INSERT INTO FILM_GENRE (GENRE_ID, FILM_ID) VALUES(?, ?)";
    private final String filmUpdateSql = "UPDATE FILM " +
            "SET NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ? " +
            "WHERE id = ? ";
    private final String rateUpdateSql = "UPDATE RATE SET RATE = ? WHERE FILM_ID = ?";
    private final String mpaUpdateSql = "UPDATE FILM_MPA SET MPA_ID = ? WHERE FILM_ID = ?";
    private final String genreUpdateSql = "DELETE FROM FILM_GENRE WHERE FILM_ID = ?";

    @Autowired
    protected FilmDaoService(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    private void addFilmToDb(FilmDb film) {
        jdbcTemplate.update(filmAddSql,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration());
    }

    private void updateFilmInDb(FilmDb film) {
        jdbcTemplate.update(filmUpdateSql,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getId());
    }

    private void addGenreToDb(FilmDb film, long filmId) {
        try {
            film.getGenres().forEach(
                    genre -> jdbcTemplate.update(genreAddSql,
                            genre.getId(),
                            filmId));
        } catch (DuplicateKeyException e) {
            log.warn(WARN_ADD_GENRE.message(), filmId, film.getGenres());
        }
    }

    private void updateGenreInFilm(FilmDb film) {
        jdbcTemplate.update(genreUpdateSql, film.getId());
        addGenreToDb(film, film.getId());
    }

    private void putRateToDb(FilmDb film, String sql, long filmId) {
        jdbcTemplate.update(sql,
                film.getRate(),
                filmId);
    }

    private void putMpaToDb(FilmDb film, String sql, long filmId) {
        jdbcTemplate.update(sql,
                film.getMpa().getId(),
                filmId);
    }

    @Override
    public FilmDb add(FilmDb film) {
        this.addFilmToDb(film);
        long filmId = super.getLastIdFromDataBase(tableName);
        this.putRateToDb(film, rateAddSql, filmId);
        if (film.getMpa() != null) {
            this.putMpaToDb(film, mpaAddSql, filmId);
        }
        if (film.getGenres().size() > 0) {
            this.addGenreToDb(film, filmId);
        }
        log.info(FILM_ADDED.message(), film.getName());
        return this.get(filmId);
    }

    @Override
    public FilmDb update(FilmDb film) {
        this.updateFilmInDb(film);
        this.putRateToDb(film, rateUpdateSql, film.getId());
        this.putMpaToDb(film, mpaUpdateSql, film.getId());
        this.updateGenreInFilm(film);
        log.info(FILM_UPDATED.message(), film.getId(), film.getName());
        return this.get(film.getId());
    }

    @Override
    public FilmDb get(long id) {
        log.info(GET_FILM.message(), id);
        return super.get(sqlReceiveFilmData, new FilmMapper(), id);
    }

    @Override
    public List<FilmDb> getList() {
        log.info(GET_FILM_LIST.message());
        return super.getList(sqlReceiveFilmData, new FilmMapper());
    }

    @Override
    public void addLike(long id, long userId) {

    }

    @Override
    public void removeLike(long id, long userId) {

    }

    @Override
    public List<FilmDb> getPopularList(long count) {
        return null;
    }

    @Override
    public void testMethod() {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
}