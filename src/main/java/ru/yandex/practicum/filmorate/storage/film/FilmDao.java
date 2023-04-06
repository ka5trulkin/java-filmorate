package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.object.ObjectNotFoundExistException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@Component
public class FilmDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Long getLastIdFromDataBase() {
        return jdbcTemplate.queryForObject("SELECT id FROM film ORDER BY id desc LIMIT 1", Long.class);
    }

    public Film add(Film film) {
        jdbcTemplate.update("INSERT INTO film(name, description, release_date, duration, mpa) VALUES(?, ?, ?, ?, ?)",
                film.getName(), film.getDescription(),film.getReleaseDate(), film.getDuration(), film.getMpa());
        return this.get(this.getLastIdFromDataBase());
    }

    public Film update(Film film) {
        jdbcTemplate.update("UPDATE film SET name=?, description=?, release_date=?, duration=?, mpa=? WHERE id=?",
                film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa(), film.getId());
        return this.get(film.getId());
    }

    public List<Film> getList() {
        return jdbcTemplate.query("SELECT * FROM film", new BeanPropertyRowMapper<>(Film.class));
    }

    public Film get(long id) {
        return jdbcTemplate.query("SELECT * FROM film WHERE id=?", new BeanPropertyRowMapper<>(Film.class), id)
                .stream()
                .findAny()
                .orElseThrow(() -> new ObjectNotFoundExistException(id));
    }
}