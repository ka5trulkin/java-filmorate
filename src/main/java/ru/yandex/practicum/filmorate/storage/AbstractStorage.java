package ru.yandex.practicum.filmorate.storage;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

import java.util.Collection;

import static ru.yandex.practicum.filmorate.message.ExceptionMessage.OBJECT_NOT_FOUND;

@AllArgsConstructor
public abstract class AbstractStorage<T> {
    protected final JdbcTemplate jdbcTemplate;

    public void add(String sql, Object... args) {
        this.update(sql, args);
    }

    public T get(String sql, RowMapper<T> mapper, Object... args) {
        return this.getList(sql, mapper, args)
                .stream()
                .findAny()
                .orElseThrow(() -> new NotFoundException(OBJECT_NOT_FOUND.message()));
    }

    public Collection<T> getList(String sql, RowMapper<T> mapper) {
        return jdbcTemplate.query(sql, mapper);
    }

    public Collection<T> getList(String sql, RowMapper<T> mapper, Object... args) {
        return jdbcTemplate.query(sql, mapper, args);
    }

    public int update(String sql, Object... args) {
        return jdbcTemplate.update(sql, args);
    }

    public void delete(String sql, Object... args) {
        int rowCount = this.update(sql, args);
        if (rowCount == 0) {
            throw new NotFoundException(OBJECT_NOT_FOUND.message());
        }
    }
}