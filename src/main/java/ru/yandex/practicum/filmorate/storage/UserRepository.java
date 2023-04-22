package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.interfaces.storage.UserStorage;
import ru.yandex.practicum.filmorate.model.user.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Objects;

import static ru.yandex.practicum.filmorate.sql.UserSql.*;

@Slf4j
@Repository
public class UserRepository extends AbstractStorage<User> implements UserStorage {
    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public User add(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(ADD_SQL.getSql(), new String[]{"ID"});
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getLogin());
            ps.setDate(4, Date.valueOf(user.getBirthday()));
            return ps;
        }, keyHolder);
        user.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return super.get(SQL_RECEIVE_BY_ID.getSql(), new BeanPropertyRowMapper<>(User.class), user.getId());
    }

    @Override
    public User update(User user) {
        super.update(
                UPDATE_SQL.getSql(),
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getBirthday(),
                user.getId());
        return super.get(SQL_RECEIVE_BY_ID.getSql(), new BeanPropertyRowMapper<>(User.class), user.getId());
    }

    @Override
    public User get(Object... args) {
        return super.get(SQL_RECEIVE_BY_ID.getSql(), new BeanPropertyRowMapper<>(User.class), args);
    }

    @Override
    public Collection<User> getList() {
        return super.getList(SQL_RECEIVE_LIST.getSql(), new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public void addFriend(long id, long friendId) {
        super.add(
                FRIEND_ADD_SQL.getSql(),
                id,
                friendId);
    }

    @Override
    public void removeFriend(long id, long friendId) {
        super.delete(
                FRIEND_DELETE_SQL.getSql(),
                id,
                friendId);
    }

    @Override
    public Collection<User> getFriendList(long id) {
        return super.getList(
                SQL_RECEIVE_FRIEND_LIST.getSql(),
                new BeanPropertyRowMapper<>(User.class),
                id);
    }

    @Override
    public Collection<User> getCommonFriendList(long id, long friendId) {
        return super.getList(
                SQL_RECEIVE_COMMON_FRIEND_LIST.getSql(),
                new BeanPropertyRowMapper<>(User.class),
                id,
                friendId);
    }

//    @Override
//    public Collection<User> getList(String sql, Object... args) {
//        return super.getList(sql, new BeanPropertyRowMapper<>(User.class), args);
//    }
}