package ru.yandex.practicum.filmorate.service.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.exception.object.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.IdHolder;

import java.util.List;

public abstract class AbstractDao<T extends IdHolder> {
    protected final JdbcTemplate jdbcTemplate;

    protected AbstractDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected Long getLastIdFromDataBase(String tableName) {
        String sql = "SELECT id FROM %s ORDER BY id DESC LIMIT 1";
        return jdbcTemplate.queryForObject(
                String.format(sql, tableName), Long.class);
    }

    public T get(String sql, RowMapper<T> mapper, long id) {
        return jdbcTemplate.query((sql), mapper, id)
                .stream()
                .findAny()
                .orElseThrow(() -> new ObjectNotFoundException(id));
    }

    public List<T> getList(String sql, RowMapper<T> mapper) {
        return jdbcTemplate.query(sql, mapper);
    }
}