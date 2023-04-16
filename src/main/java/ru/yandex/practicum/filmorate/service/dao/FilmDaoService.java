package ru.yandex.practicum.filmorate.service.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.film.BadFilmLikeException;
import ru.yandex.practicum.filmorate.exception.film.FilmLikeAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.film.FilmLikeNotFoundException;
import ru.yandex.practicum.filmorate.model.film.FilmDb;
import ru.yandex.practicum.filmorate.service.interfaces.FilmDao;

import java.util.List;

import static ru.yandex.practicum.filmorate.message.FilmLogMessage.*;

@Slf4j
@Service
public class FilmDaoService extends AbstractDao<FilmDb> implements FilmDao<FilmDb> {
    private final String tableName = "FILM_DB";
    private final String sqlReceiveFilmList = "SELECT " +
            "f.ID, f.NAME, f.DESCRIPTION, f.RELEASE_DATE, f.DURATION, " +
            "r.COUNTER rate, " +
            "m.ID mpaId, m.NAME mpaName, " +
            "g.ID genreId, g.NAME genreName " +
            "FROM FILM_DB f " +
            "LEFT JOIN FILM_GENRE fg ON f.ID = fg.FILM_ID " +
            "LEFT JOIN GENRE g ON fg.GENRE_ID = g.ID " +
            "LEFT JOIN FILM_MPA fm ON fm.FILM_ID = f.ID " +
            "LEFT JOIN MPA m ON fm.MPA_ID = m.ID " +
            "LEFT JOIN RATE r ON f.ID = r.FILM_ID";
    private final String sqlReceiveFilmById = String.join(" ", sqlReceiveFilmList, "WHERE f.ID = ?");
    private final String filmAddSql = "INSERT INTO FILM_DB (NAME, DESCRIPTION, RELEASE_DATE, DURATION) VALUES(?, ?, ?, ?)";
    private final String rateAddSql = "INSERT INTO RATE (COUNTER, FILM_ID) VALUES(?, ?)";
    private final String rateUpdateSql = "UPDATE RATE SET COUNTER = ? WHERE FILM_ID = ?";
    private final String incrementRateSql = "UPDATE RATE SET COUNTER = COUNTER + 1 WHERE FILM_ID = ?";
    private final String decrementRateSql = "MERGE INTO RATE R USING (SELECT FILM_ID, USER_ID FROM LIKES) L ON (L.FILM_ID = ? AND L.USER_ID = ?) WHEN MATCHED AND R.COUNTER > 0 THEN UPDATE SET R.COUNTER = R.COUNTER - 1";
    private final String mpaAddSql = "INSERT INTO FILM_MPA (MPA_ID, FILM_ID) VALUES(?, ?)";
    private final String genreAddSql = "INSERT INTO FILM_GENRE (GENRE_ID, FILM_ID) VALUES(?, ?)";
    private final String filmUpdateSql = "UPDATE FILM_DB " +
            "SET NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ? " +
            "WHERE ID = ? ";
    private final String mpaUpdateSql = "UPDATE FILM_MPA SET MPA_ID = ? WHERE FILM_ID = ?";
    private final String genreDeleteSql = "DELETE FROM FILM_GENRE WHERE FILM_ID = ?";
    private final String likeAddSql = "INSERT INTO LIKES (FILM_ID, USER_ID) VALUES(?, ?)";
    private final String likeDeleteSql = "DELETE FROM LIKES WHERE FILM_ID = ? AND USER_ID = ?";
    private final String checkIsLiked = "SELECT LIKED FROM LIKES WHERE FILM_ID = %d AND USER_ID = %d";

    @Autowired
    protected FilmDaoService(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    private void addFilmToDb(FilmDb film) {
        jdbcTemplate.update(
                filmAddSql,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration());
    }

    private void updateFilmInDb(FilmDb film) {
        jdbcTemplate.update(
                filmUpdateSql,
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
        jdbcTemplate.update(genreDeleteSql, film.getId());
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
                incrementRateSql,
                filmId);
    }

    private void decrementRate(long filmId, long userID) {
        jdbcTemplate.update(decrementRateSql, filmId, userID);
    }

    private void checkIsLiked(long filmId, long userId) {
        try {
            jdbcTemplate.queryForObject(
                    String.format(checkIsLiked, filmId, userId),
                    Boolean.class);
        } catch (EmptyResultDataAccessException e) {
            throw new FilmLikeNotFoundException(filmId, userId);
        }
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
        return super.get(sqlReceiveFilmById, new FilmMapper(), filmId);
    }

    @Override
    public FilmDb update(FilmDb film) {
        this.updateFilmInDb(film);
        this.putRateToDb(film, rateUpdateSql, film.getId());
        this.putMpaToDb(film, mpaUpdateSql, film.getId());
        this.updateGenreInFilm(film);
        log.info(FILM_UPDATED.message(), film.getId(), film.getName());
        return super.get(sqlReceiveFilmById, new FilmMapper(), film.getId());
    }

    @Override
    public FilmDb get(long id) {
        log.info(GET_FILM.message(), id);
        return super.get(sqlReceiveFilmById, new FilmMapper(), id);
    }

    @Override
    public List<FilmDb> getList() {
        log.info(GET_FILM_LIST.message());
        return super.getList(sqlReceiveFilmList, new FilmMapper());
    }

    @Override
    public void addLike(long id, long userId) {
        try {
            jdbcTemplate.update(
                    likeAddSql,
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
                    likeDeleteSql,
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
        return null;
    }

    @Override
    public void testMethod() {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
}