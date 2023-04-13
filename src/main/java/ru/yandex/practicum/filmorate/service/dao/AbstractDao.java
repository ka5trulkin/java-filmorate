package ru.yandex.practicum.filmorate.service.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.exception.object.ObjectNotFoundExistException;
import ru.yandex.practicum.filmorate.model.IdHolder;

public abstract class AbstractDao<T extends IdHolder> {
    protected final JdbcTemplate jdbcTemplate;

    protected AbstractDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected Long getLastIdFromDataBase(String tableName) {
        return jdbcTemplate.queryForObject(
                String.format("SELECT id FROM %s ORDER BY id DESC LIMIT 1", tableName), Long.class);
    }

    public T get(String sql, RowMapper<T> mapper, long id) {
        return jdbcTemplate.query((sql + " WHERE f.id = ?"), mapper, id)
                .stream()
                .findAny()
                .orElseThrow(() -> new ObjectNotFoundExistException(id));
    }
}