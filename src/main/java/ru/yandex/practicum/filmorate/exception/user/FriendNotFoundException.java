package ru.yandex.practicum.filmorate.exception.user;

import ru.yandex.practicum.filmorate.exception.NotFoundException;

import static ru.yandex.practicum.filmorate.message.ExceptionMessage.FRIEND_NOT_FOUND;

public class FriendNotFoundException extends NotFoundException {
    public FriendNotFoundException(long id, long friendId) {
        super(String.format(FRIEND_NOT_FOUND.message(), id, friendId));
    }
}