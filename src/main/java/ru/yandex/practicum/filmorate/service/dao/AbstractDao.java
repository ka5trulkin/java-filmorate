package ru.yandex.practicum.filmorate.service.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class AbstractDao {
    protected final JdbcTemplate jdbcTemplate;

    @Autowired
    protected AbstractDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected Long getLastIdFromDataBase(String tableName) {
        return jdbcTemplate.queryForObject(
                String.format("SELECT id FROM %s ORDER BY id DESC LIMIT 1", tableName), Long.class);
    }
}