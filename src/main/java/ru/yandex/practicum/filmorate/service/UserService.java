package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.message.UserLogMessage.*;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserStorage storage;

    private void checkName(User user) {
        if ((user.getName() == null) || (user.getName().isBlank())) {
            user.setName(user.getLogin());
        }
    }

    public User add(User user) {
        this.checkName(user);
        log.info(USER_ADDED.message(), user.getLogin(), user.getId());
        return storage.add(user);
    }

    public User update(User user) {
        this.checkName(user);
        log.info(USER_UPDATED.message(), user.getId(), user.getLogin());
        return storage.update(user);
    }

    public User get(long id) {
        log.info(GET_USER.message(), id);
        return storage.get(id);
    }

    public List<User> getList() {
        log.info(GET_USER_LIST.message());
        return storage.getList();
    }

    public void addFriend(long id, long friendId) {
        storage.get(id).getFriends().add(friendId);
        storage.get(friendId).getFriends().add(id);
        log.info(USER_FRIEND_ADDED.message(), id, friendId);
    }

    public void removeFriend(long id, long friendId) {
        Set<Long> userFriendsList = storage.get(id).getFriends();
        Set<Long> friendFriendsList = storage.get(friendId).getFriends();
        userFriendsList.remove(friendId);
        friendFriendsList.remove(id);
        log.info(USER_FRIEND_REMOVED.message(), id, friendId);
    }

    public List<User> getFriendList(long id) {
        Set<Long> friendList = storage.get(id).getFriends();
        return storage.getList().stream()
                .filter(user -> friendList.contains(user.getId()))
                .collect(Collectors.toList());
    }

    public List<User> getCommonFriendList(long id, long otherId) {
        List<User> userFriends = this.getFriendList(id);
        List<User> otherFriends = this.getFriendList(otherId);
        log.info(GET_USER_COMMON_FRIEND_LIST.message(), id, otherId);
        return userFriends.stream()
                .filter(otherFriends::contains)
                .collect(Collectors.toList());
    }
}