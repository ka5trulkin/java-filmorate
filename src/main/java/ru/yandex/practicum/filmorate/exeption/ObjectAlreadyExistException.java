package ru.yandex.practicum.filmorate.exeption;

import static ru.yandex.practicum.filmorate.message.ExceptionMessage.OBJECT_ALREADY_EXIST;

public class ObjectAlreadyExistException extends RequestException {
    public ObjectAlreadyExistException(long id) {
        super(String.format(OBJECT_ALREADY_EXIST.message(), id));
    }
}