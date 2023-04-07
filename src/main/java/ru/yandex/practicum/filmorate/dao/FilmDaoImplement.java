package ru.yandex.practicum.filmorate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.interfaces.FilmDao;
import ru.yandex.practicum.filmorate.exception.object.ObjectNotFoundExistException;
import ru.yandex.practicum.filmorate.model.film.Film;

import java.util.List;

@Service
public class FilmDaoImplement extends AbstractDao implements FilmDao<Film> {
    @Autowired
    protected FilmDaoImplement(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Film add(Film object) {
        jdbcTemplate.update("INSERT INTO film(name, description, release_date, duration, mpa) VALUES(?, ?, ?, ?, ?)",
                object.getName(), object.getDescription(), object.getReleaseDate(), object.getDuration(), object.getMpa());
        String tableName = "film";
        return this.get(this.getLastIdFromDataBase(tableName));
    }

    @Override
    public Film update(Film object) {
        jdbcTemplate.update("UPDATE film SET name=?, description=?, release_date=?, duration=?, mpa=? WHERE id=?",
                object.getName(), object.getDescription(), object.getReleaseDate(), object.getDuration(), object.getMpa(), object.getId());
        return this.get(object.getId());
    }

    @Override
    public Film get(long id) {
        return jdbcTemplate.query("SELECT * FROM film WHERE id=?", new BeanPropertyRowMapper<>(Film.class), id)
                .stream()
                .findAny()
                .orElseThrow(() -> new ObjectNotFoundExistException(id));
    }

    @Override
    public List<Film> getList() {
        return jdbcTemplate.query("SELECT * FROM film", new BeanPropertyRowMapper<>(Film.class));
    }

    @Override
    public void addLike(long id, long userId) {

    }

    @Override
    public void removeLike(long id, long userId) {

    }

    @Override
    public List<Film> getPopularList(long count) {
        return null;
    }
}