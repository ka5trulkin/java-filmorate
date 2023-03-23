package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.message.UserLogMessage.*;

@Slf4j
@Service
public class UserService {
    @Autowired
    UserStorage storage;

    public User add(User user) {
        log.info(USER_ADDED.message(), user.getLogin(), user.getId());
        return storage.add(user);
    }

    public User update(User user) {
        log.info(USER_UPDATED.message(), user.getId(), user.getLogin());
        return storage.update(user);
    }

    public List<User> getList() {
        log.info(GET_USER_LIST.message());
        return storage.getList();
    }

    public User get(long id) {
        log.info(GET_USER.message(), id);
        return storage.get(id);
    }

    public void addFriend(long id, long friendId) {
        storage.get(id).getFriends().add(friendId);
        storage.get(friendId).getFriends().add(id);
        log.info(USER_FRIEND_ADDED.message(), id, friendId);
    }

    public void removeFriend(long id, long friendId) {
        storage.get(id).getFriends().remove(friendId);
        storage.get(friendId).getFriends().remove(id);
        log.info(USER_FRIEND_REMOVED.message(), id, friendId);
    }

    public List<User> getFriendList(long id) {
        log.info(GET_USER_FRIEND_LIST.message(), id);
        return storage.getFriendList(id);
    }

    public List<User> getCommonFriendList(long id, long otherId) {
        log.info(GET_USER_COMMON_FRIEND_LIST.message(), id, otherId);
        return storage.getList().stream()
                .filter(user -> storage.getFriendList(id).contains(user)
                        && storage.getFriendList(otherId).contains(user))
                .collect(Collectors.toList());
    }
}