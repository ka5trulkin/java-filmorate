package ru.yandex.practicum.filmorate.exeption.user;

import ru.yandex.practicum.filmorate.exeption.RequestException;

import static ru.yandex.practicum.filmorate.message.ExceptionMessage.*;

public class UserAlreadyExistException extends RequestException {
    public UserAlreadyExistException(long id) {
        super(String.format(USER_ALREADY_EXIST.message(), id));
    }
}