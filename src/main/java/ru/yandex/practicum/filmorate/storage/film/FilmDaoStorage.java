package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.object.ObjectNotFoundExistException;
import ru.yandex.practicum.filmorate.model.film.FilmDB;
import ru.yandex.practicum.filmorate.storage.AbstractDaoStorage;

import java.util.List;

@Component
public class FilmDaoStorage extends AbstractDaoStorage<FilmDB> {
    @Autowired
    public FilmDaoStorage(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public FilmDB add(FilmDB object) {
        jdbcTemplate.update("INSERT INTO film(name, description, release_date, duration, mpa) VALUES(?, ?, ?, ?, ?)",
                object.getName(), object.getDescription(), object.getReleaseDate(), object.getDuration(), object.getMpa());
        String tableName = "film";
        return this.get(this.getLastIdFromDataBase(tableName));
    }

    @Override
    public FilmDB update(FilmDB object) {
        jdbcTemplate.update("UPDATE film SET name=?, description=?, release_date=?, duration=?, mpa=? WHERE id=?",
                object.getName(), object.getDescription(), object.getReleaseDate(), object.getDuration(), object.getMpa(), object.getId());
        return this.get(object.getId());
    }

    @Override
    public FilmDB get(long id) {
        return jdbcTemplate.query("SELECT * FROM film WHERE id=?", new BeanPropertyRowMapper<>(FilmDB.class), id)
                .stream()
                .findAny()
                .orElseThrow(() -> new ObjectNotFoundExistException(id));
    }

    @Override
    public List<FilmDB> getList() {
        return jdbcTemplate.query("SELECT * FROM film", new BeanPropertyRowMapper<>(FilmDB.class));
    }
}