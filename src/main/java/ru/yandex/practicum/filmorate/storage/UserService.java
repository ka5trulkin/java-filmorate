package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.user.User;

import java.util.List;

public interface UserService extends Service<User> {
    void addFriend(long id, long friendId);

    void removeFriend(long id, long friendId);

    List<User> getFriendList(long id);

    List<User> getCommonFriendList(long id, long otherId);
}