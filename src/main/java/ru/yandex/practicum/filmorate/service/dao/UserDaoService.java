package ru.yandex.practicum.filmorate.service.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.user.UserDb;
import ru.yandex.practicum.filmorate.service.interfaces.UserDao;

import java.util.List;

public class UserDaoService extends AbstractDao<UserDb> implements UserDao<UserDb> {

    @Autowired
    protected UserDaoService(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public UserDb add(UserDb object) {
        return null;
    }

    @Override
    public UserDb update(UserDb object) {
        return null;
    }

    @Override
    public UserDb get(long id) {
        return null;
    }

    @Override
    public List<UserDb> getList() {
        return null;
    }

    @Override
    public void testMethod() {

    }

    @Override
    public void addFriend(long id, long friendId) {

    }

    @Override
    public void removeFriend(long id, long friendId) {

    }

    @Override
    public List<UserDb> getFriendList(long id) {
        return null;
    }

    @Override
    public List<UserDb> getCommonFriendList(long id, long otherId) {
        return null;
    }
}