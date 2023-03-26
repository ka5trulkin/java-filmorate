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
public class UserService extends AbstractService<User> {
    private UserService(@Autowired UserStorage storage) {
        super(storage);
    }

    private void checkName(User user) {
        if ((user.getName() == null) || (user.getName().isBlank())) {
            user.setName(user.getLogin());
        }
    }

    private Set<Long> getIdFriendList(long id) {
        return super.get(id).getFriends();
    }

    @Override
    public User add(User user) {
        this.checkName(user);
        log.info(USER_ADDED.message(), user.getLogin());
        return super.add(user);
    }

    @Override
    public User update(User user) {
        this.checkName(user);
        log.info(USER_UPDATED.message(), user.getId(), user.getLogin());
        return super.update(user);
    }

    @Override
    public User get(long id) {
        log.info(GET_USER.message(), id);
        return super.get(id);
    }

    @Override
    public List<User> getList() {
        log.info(GET_USER_LIST.message());
        return super.getList();
    }

    public void addFriend(long id, long friendId) {
        Set<Long> userFriendList = this.getIdFriendList(id);
        Set<Long> friendFriendList = this.getIdFriendList(friendId);
        userFriendList.add(friendId);
        friendFriendList.add(id);
        log.info(USER_FRIEND_ADDED.message(), id, friendId);
    }

    public void removeFriend(long id, long friendId) {
        Set<Long> userFriendList = this.getIdFriendList(id);
        Set<Long> friendFriendList = this.getIdFriendList(friendId);
        userFriendList.remove(friendId);
        friendFriendList.remove(id);
        log.info(USER_FRIEND_REMOVED.message(), id, friendId);
    }

    public List<User> getFriendList(long id) {
        Set<Long> friendList = super.get(id).getFriends();
        log.info(GET_USER_FRIEND_LIST.message(), id);
        return super.getList().stream()
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