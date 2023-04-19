package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.user.FriendNotFoundException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.Storage;
import ru.yandex.practicum.filmorate.storage.UserService;

import java.util.Collection;

import static ru.yandex.practicum.filmorate.message.UserLogMessage.*;
import static ru.yandex.practicum.filmorate.service.dao.user.UserSql.*;

@Slf4j
@Service
public class UserServiceImplement extends AbstractService<User> implements UserService {
    @Autowired
    protected UserServiceImplement(@Qualifier("userStorage") Storage<User> storage) {
        super(storage);
    }

    @Override
    public User add(User user) {
        log.info(USER_ADDED.message(), user.getLogin());
        return super.add(user);
    }

    @Override
    public void addFriend(long id, long friendId) {
        try {
            super.add(FRIEND_ADD_SQL.getSql(), id, friendId);
        } catch (DuplicateKeyException e) {
            log.warn(WARN_FRIENDSHIP_ALREADY_EXIST.message(), id, friendId);
        } catch (DataIntegrityViolationException e) {
            throw new FriendNotFoundException(id, friendId);
        }
    }

    @Override
    public User update(User user) {
        log.info(USER_UPDATED.message(), user.getId(), user.getLogin());
        return super.update(user);
    }

    @Override
    public void removeFriend(long id, long friendId) {

    }

    @Override
    public User get(long id) {
        log.info(GET_USER.message(), id);
        return super.get(SQL_RECEIVE_BY_ID.getSql(), id);
    }

    @Override
    public Collection<User> getList() {
        log.info(GET_USER_LIST.message());
        return super.getList(SQL_RECEIVE_LIST.getSql());
    }

    @Override
    public Collection<User> getFriendList(long id) {
        return null;
    }

    @Override
    public Collection<User> getCommonFriendList(long id, long otherId) {
        return null;
    }
}