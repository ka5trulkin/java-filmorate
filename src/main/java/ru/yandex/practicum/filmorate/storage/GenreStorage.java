package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.film.Genre;

import java.util.Collection;

@Slf4j
@Repository
public class GenreStorage extends AbstractStorage<Genre> implements TinyStorage<Genre> {
    @Autowired
    public GenreStorage(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Genre get(String sql, Object... args) {
        return super.get(sql, new BeanPropertyRowMapper<>(Genre.class), args);
    }

    @Override
    public Collection<Genre> getList(String sql) {
        return super.getList(sql, new BeanPropertyRowMapper<>(Genre.class));
    }
}