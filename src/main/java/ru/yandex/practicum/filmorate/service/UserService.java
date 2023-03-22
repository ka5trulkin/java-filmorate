package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.user.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import static ru.yandex.practicum.filmorate.message.LogMessage.*;

@Slf4j
@Service
public class UserService {
    @Autowired
    UserStorage storage;

    private void checkUserNotNull(User user, long id) {
        if (user == null) {
            throw new UserNotFoundException(id);
        }
    }

    public void addFriend(long id, long friendId) {
        User user = storage.get(id);
        User friend = storage.get(friendId);
        checkUserNotNull(user, id);
        checkUserNotNull(friend, friendId);
        user.getFriendList().add(friendId);
        friend.getFriendList().add(id);
        log.info(USER_FRIEND_ADDED.message(), id, friendId);
    }
}