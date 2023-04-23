package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.interfaces.storage.GenreStorage;
import ru.yandex.practicum.filmorate.model.film.Genre;

import java.util.Collection;

import static ru.yandex.practicum.filmorate.sql.GenreSql.*;

@Slf4j
@Repository
public class GenreRepository extends AbstractStorage<Genre> implements GenreStorage {
    @Autowired
    public GenreRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Genre add(Genre genre) {
        super.add(GENRE_PUT_SQL.getSql(), genre.getId(), genre.getName());
        return super.get(GENRE_GET_SQL.getSql(), new BeanPropertyRowMapper<>(Genre.class), genre.getId());
    }

    @Override
    public Genre update(Genre genre) {
        super.update(GENRE_PUT_SQL.getSql(), genre.getId(), genre.getName());
        return super.get(GENRE_GET_SQL.getSql(), new BeanPropertyRowMapper<>(Genre.class), genre.getId());
    }

    @Override
    public Genre get(Object... args) {
        return super.get(GENRE_GET_SQL.getSql(), new BeanPropertyRowMapper<>(Genre.class), args);
    }

    @Override
    public Collection<Genre> getList() {
        return super.getList(GENRE_GET_LIST_SQL.getSql(), new BeanPropertyRowMapper<>(Genre.class));
    }
}