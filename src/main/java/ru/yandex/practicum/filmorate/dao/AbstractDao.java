package ru.yandex.practicum.filmorate.dao;

import org.springframework.jdbc.core.JdbcTemplate;

public abstract class AbstractDao {
    protected final JdbcTemplate jdbcTemplate;

    protected AbstractDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected Long getLastIdFromDataBase(String tableName) {
        return jdbcTemplate.queryForObject(
                String.format("SELECT (id) FROM (%s) ORDER BY (id) DESC LIMIT (1)", tableName), Long.class);
    }
}