package ru.yandex.practicum.filmorate.exception.object;

import ru.yandex.practicum.filmorate.exception.NotFoundException;

import static ru.yandex.practicum.filmorate.message.ExceptionMessage.OBJECT_NOT_FOUND;

public class ObjectNotFoundException extends NotFoundException {
    public ObjectNotFoundException(long id) {
        super(String.format(OBJECT_NOT_FOUND.message(), id));
    }
}