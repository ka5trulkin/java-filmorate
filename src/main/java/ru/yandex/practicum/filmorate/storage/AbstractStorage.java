package ru.yandex.practicum.filmorate.storage;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.IdHolder;

import java.util.Collection;

import static ru.yandex.practicum.filmorate.message.ExceptionMessage.OBJECT_NOT_FOUND;

@AllArgsConstructor
public abstract class AbstractStorage<T extends IdHolder> implements Storage<T> {
    protected final JdbcTemplate jdbcTemplate;

    @Override
    public void add(String sql, Object... args) {
        jdbcTemplate.update(sql, args);
    }

    public T get(String sql, RowMapper<T> mapper, Object... args) {
        return jdbcTemplate.query((sql), mapper, args)
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

    public void update(String sql, Object... args){
        jdbcTemplate.update(sql, args);
    }

    @Override
    public void delete(String sql, Object... args) {
        int rowCount = jdbcTemplate.update(sql, args);
        if (rowCount == 0) {
            throw new NotFoundException(OBJECT_NOT_FOUND.message());
        }
    }
}