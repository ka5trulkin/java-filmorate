package ru.yandex.practicum.filmorate.exeption.user;

import ru.yandex.practicum.filmorate.exeption.NoDataException;

public class UserNotFoundException extends NoDataException {
    public UserNotFoundException(long id) {
        super(String.format("Пользователь ID:%s не найден", id));
    }
}