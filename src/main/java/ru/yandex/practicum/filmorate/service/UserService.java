package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.user.UserNotFoundException;
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

    private User getFromStorage(long id) {
        User user = storage.get(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        return user;
    }

    public void addFriend(long id, long friendId) {
        User user = getFromStorage(id);
        User friend = getFromStorage(friendId);
        user.getFriendList().add(friendId);
        friend.getFriendList().add(id);
        log.info(USER_FRIEND_ADDED.message(), id, friendId);
    }

    public void removeFriend(long id, long friendId) {
        User user = getFromStorage(id);
        User friend = getFromStorage(friendId);
        user.getFriendList().remove(friendId);
        friend.getFriendList().remove(id);
        log.info(USER_FRIEND_REMOVED.message(), id, friendId);
    }

    public List<User> getFriendList(long id) {
        log.info(GET_USER_FRIEND_LIST.message(), id);
        return storage.getFriendIdList(id);
    }

    public List<User> getCommonFriendList(long id, long otherId) {
        List<User> userFriendList = storage.getFriendIdList(id);
        List<User> otherFriendList = storage.getFriendIdList(otherId);
        log.info(GET_USER_COMMON_FRIEND_LIST.message(), id, otherId);
        return storage.getList().stream()
                .filter(user -> userFriendList.contains(user) && otherFriendList.contains(user))
                .collect(Collectors.toList());
    }
}