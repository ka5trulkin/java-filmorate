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
    private final String sqlReceiveFilmData =
            "SELECT f.id, f.name, f.description, f.release_date, f.duration, g.id genreId, g.name genreName " +
            "FROM film f " +
            "LEFT JOIN film_genre fg ON f.id = fg.film_id " +
            "LEFT JOIN genre g ON fg.genre_id = g.id";

    @Autowired
    protected FilmDaoService(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public FilmDb add(FilmDb film) {
        jdbcTemplate.update("INSERT INTO film(name, description, release_date, duration) VALUES(?, ?, ?, ?)",
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration());
        String tableName = "film";
        return this.get(super.getLastIdFromDataBase(tableName));
    }

    @Override
    public FilmDb update(FilmDb film) {
        jdbcTemplate.update("UPDATE film SET name=?, description=?, release_date=?, duration=?, mpa=? WHERE id=?",
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa(),
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