package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.user.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

import static ru.yandex.practicum.filmorate.service.dao.user.UserSql.SQL_RECEIVE_BY_ID;
import static ru.yandex.practicum.filmorate.service.dao.user.UserSql.USER_ADD_SQL;

@Slf4j
@Repository
public class UserStorage extends AbstractStorage<User> implements Storage<User> {
    @Autowired
    public UserStorage(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public User add(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(USER_ADD_SQL.getSql(), new String[]{"ID"});
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getLogin());
            ps.setDate(4, Date.valueOf(user.getBirthday()));
            return ps;
        }, keyHolder);
        user.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return super.get(SQL_RECEIVE_BY_ID.getSql(), user.getId(), new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public User update(User object) {
        return null;
    }

    @Override
    public User get(String sql, long id) {
        return super.get(sql, id, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public List<User> getList(String sql) {
        return super.getList(sql, new BeanPropertyRowMapper<>(User.class));
    }
}