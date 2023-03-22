package ru.yandex.practicum.filmorate.exeption;

import ru.yandex.practicum.filmorate.message.ExceptionMessage;

public class UserAlreadyExistException extends RequestException {
    public UserAlreadyExistException(long id) {
        super(String.format(ExceptionMessage.USER_ALREADY_EXISTS.message(), id));
    }
}
