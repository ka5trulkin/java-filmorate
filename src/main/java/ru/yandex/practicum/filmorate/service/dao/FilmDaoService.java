package ru.yandex.practicum.filmorate.service.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.service.interfaces.FilmDao;
import ru.yandex.practicum.filmorate.exception.object.ObjectNotFoundExistException;
import ru.yandex.practicum.filmorate.model.film.FilmDb;

import java.util.List;

@Service
public class FilmDaoService extends AbstractDao implements FilmDao<FilmDb> {
    @Autowired
    protected FilmDaoService(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public FilmDb add(FilmDb object) {
        jdbcTemplate.update("INSERT INTO film(name, description, release_date, duration, mpa) VALUES(?, ?, ?, ?, ?)",
                object.getName(), object.getDescription(), object.getReleaseDate(), object.getDuration(), object.getMpa());
        String tableName = "film";
        return this.get(this.getLastIdFromDataBase(tableName));
    }

    @Override
    public FilmDb update(FilmDb object) {
        jdbcTemplate.update("UPDATE film SET name=?, description=?, release_date=?, duration=?, mpa=? WHERE id=?",
                object.getName(), object.getDescription(), object.getReleaseDate(), object.getDuration(), object.getMpa(), object.getId());
        return this.get(object.getId());
    }

    @Override
    public FilmDb get(long id) {
        return jdbcTemplate.query("SELECT * FROM film WHERE id=?", new BeanPropertyRowMapper<>(FilmDb.class), id)
                .stream()
                .findAny()
                .orElseThrow(() -> new ObjectNotFoundExistException(id));
    }

    @Override
    public List<FilmDb> getList() {
        return jdbcTemplate.query("SELECT * FROM film", new BeanPropertyRowMapper<>(FilmDb.class));
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