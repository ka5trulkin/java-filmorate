package ru.yandex.practicum.filmorate.interfaces.service;

import ru.yandex.practicum.filmorate.model.user.User;

import java.util.Collection;

public interface UserService extends Service<User> {
    void addFriend(long id, long friendId);

    void removeFriend(long id, long friendId);

    Collection<User> getFriendList(long id);

    Collection<User> getCommonFriendList(long id, long otherId);
}