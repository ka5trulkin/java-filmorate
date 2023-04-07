package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.IdHolder;
import ru.yandex.practicum.filmorate.storage.Storage;

public abstract class AbstractDaoStorage<T extends IdHolder> implements Storage<T> {
    protected final JdbcTemplate jdbcTemplate;

    public AbstractDaoStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected Long getLastIdFromDataBase(String tableName) {
        return jdbcTemplate.queryForObject(
                String.format("SELECT (id) FROM (%s) ORDER BY (id) DESC LIMIT (1)", tableName), Long.class);
    }
}