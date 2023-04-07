package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.interfaces.Dao;
import ru.yandex.practicum.filmorate.model.user.UserInMemory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.message.UserLogMessage.*;

@Slf4j
@Service
public class UserService extends AbstractService<UserInMemory> {
    @Autowired
    private UserService(@Qualifier("userStorage") Dao<UserInMemory> storage) {
        super(storage);
    }

    private void checkName(UserInMemory user) {
        if ((user.getName() == null) || (user.getName().isBlank())) {
            user.setName(user.getLogin());
        }
    }

    private Set<Long> getIdFriendList(long id) {
        return super.get(id).getFriends();
    }

    @Override
    public UserInMemory add(UserInMemory user) {
        this.checkName(user);
        log.info(USER_ADDED.message(), user.getLogin());
        return super.add(user);
    }

    @Override
    public UserInMemory update(UserInMemory user) {
        this.checkName(user);
        log.info(USER_UPDATED.message(), user.getId(), user.getLogin());
        return super.update(user);
    }

    @Override
    public UserInMemory get(long id) {
        log.info(GET_USER.message(), id);
        return super.get(id);
    }

    @Override
    public List<UserInMemory> getList() {
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

    public List<UserInMemory> getFriendList(long id) {
        Set<Long> friendList = super.get(id).getFriends();
        log.info(GET_USER_FRIEND_LIST.message(), id);
        return friendList.stream()
                .map(super::get)
                .collect(Collectors.toList());
    }

    public List<UserInMemory> getCommonFriendList(long id, long otherId) {
        List<UserInMemory> userFriends = this.getFriendList(id);
        List<UserInMemory> otherFriends = this.getFriendList(otherId);
        log.info(GET_USER_COMMON_FRIEND_LIST.message(), id, otherId);
        return userFriends.stream()
                .filter(otherFriends::contains)
                .collect(Collectors.toList());
    }
}