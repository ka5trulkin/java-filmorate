package ru.yandex.practicum.filmorate.exeption;

import static ru.yandex.practicum.filmorate.message.ExceptionMessage.OBJECT_NOT_FOUND;

public class ObjectNotFoundExistException extends NotFoundException {
    public ObjectNotFoundExistException(long id) {
        super(String.format(OBJECT_NOT_FOUND.message(), id));
    }
}