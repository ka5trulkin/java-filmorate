package ru.yandex.practicum.filmorate.storage;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.yandex.practicum.filmorate.exception.object.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.IdHolder;
import ru.yandex.practicum.filmorate.model.user.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

import static ru.yandex.practicum.filmorate.service.dao.user.UserSql.SQL_RECEIVE_BY_ID;
import static ru.yandex.practicum.filmorate.service.dao.user.UserSql.USER_ADD_SQL;

@AllArgsConstructor
public abstract class AbstractStorage<T extends IdHolder> {
    protected final JdbcTemplate jdbcTemplate;

    public T get(String sql, long id, RowMapper<T> mapper) {
        return jdbcTemplate.query((sql), mapper, id)
                .stream()
                .findAny()
                .orElseThrow(() -> new ObjectNotFoundException(id));
    }

    public List<T> getList(String sql, RowMapper<T> mapper) {
        return jdbcTemplate.query(sql, mapper);
    }
}