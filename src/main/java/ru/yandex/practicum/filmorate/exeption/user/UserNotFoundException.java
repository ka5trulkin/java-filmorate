package ru.yandex.practicum.filmorate.exeption.user;

import ru.yandex.practicum.filmorate.exeption.NoDataException;

import static ru.yandex.practicum.filmorate.message.ExceptionMessage.*;

public class UserNotFoundException extends NoDataException {
    public UserNotFoundException(long id) {
        super(String.format(USER_NOT_FOUND.message(), id));
    }
}