package ru.yandex.practicum.filmorate.exeption;

import ru.yandex.practicum.filmorate.message.ExceptionMessage;

public class FilmAlreadyExistException extends RequestException {
    public FilmAlreadyExistException(long id) {
        super(String.format(ExceptionMessage.FILM_ALREADY_EXISTS.message(), id));
    }
}
