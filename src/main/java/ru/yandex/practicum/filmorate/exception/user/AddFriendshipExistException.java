package ru.yandex.practicum.filmorate.exception.user;

import ru.yandex.practicum.filmorate.exception.RequestException;

import static ru.yandex.practicum.filmorate.message.ExceptionMessage.ADD_FRIENDSHIP;

public class AddFriendshipExistException extends RequestException {
    public AddFriendshipExistException(long id, long friendId) {
        super(String.format(ADD_FRIENDSHIP.message(), id, friendId));
    }
}