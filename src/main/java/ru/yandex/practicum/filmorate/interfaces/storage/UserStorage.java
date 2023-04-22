package ru.yandex.practicum.filmorate.interfaces.storage;

import ru.yandex.practicum.filmorate.model.user.User;

import java.util.Collection;

public interface UserStorage extends Storage<User> {
    void addFriend(long id, long friendId);

    void removeFriend(long id, long friendId);

    Collection<User> getFriendList(long id);

    Collection<User> getCommonFriendList(long id, long otherId);
}
