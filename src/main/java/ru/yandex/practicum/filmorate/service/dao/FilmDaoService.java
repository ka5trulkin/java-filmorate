package ru.yandex.practicum.filmorate.service.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.object.ObjectNotFoundExistException;
import ru.yandex.practicum.filmorate.model.film.FilmDb;
import ru.yandex.practicum.filmorate.service.interfaces.FilmDao;

import java.util.List;

@Service
public class FilmDaoService extends AbstractDao implements FilmDao<FilmDb> {
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

    @Autowired
    protected FilmDaoService(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public FilmDb add(FilmDb film) {
        long filmId;
        String tableName = "film";
        jdbcTemplate.update("INSERT INTO film(name, description, release_date, duration) VALUES(?, ?, ?, ?)",
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration());
        filmId = super.getLastIdFromDataBase(tableName);
        jdbcTemplate.update("INSERT INTO RATE (FILM_ID, RATE) VALUES(?, ?)",
                filmId,
                film.getRate());
        if (film.getMpa() != null) {
            jdbcTemplate.update("INSERT INTO FILM_MPA (FILM_ID, MPA_ID) VALUES(?, ?)",
                    filmId,
                    film.getMpa().getId());
        }
        if (film.getGenres().size() > 0) {
            film.getGenres().forEach(
                    genre -> jdbcTemplate.update("INSERT INTO FILM_GENRE (FILM_ID, GENRE_ID) VALUES(?, ?)",
                            filmId,
                            genre.getId()));
        }
        return this.get(filmId);
    }

    @Override
    public FilmDb update(FilmDb film) {
        jdbcTemplate.update("UPDATE FILM " +
                        "SET NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ? " +
                        "WHERE id = ? ",
        film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getId());
        return this.get(film.getId());
    }

    @Override
    public FilmDb get(long id) {
        return jdbcTemplate.query((sqlReceiveFilmData + " WHERE f.id = ?"), new FilmMapper(), id)
                .stream()
                .findAny()
                .orElseThrow(() -> new ObjectNotFoundExistException(id));
    }

    @Override
    public List<FilmDb> getList() {
        return jdbcTemplate.query(sqlReceiveFilmData, new FilmMapper());
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