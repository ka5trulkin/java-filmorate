package ru.yandex.practicum.filmorate.exeption.film;

import ru.yandex.practicum.filmorate.exeption.RequestException;

import static ru.yandex.practicum.filmorate.message.ExceptionMessage.FILM_ALREADY_EXIST;

public class FilmAlreadyExistException extends RequestException {
    public FilmAlreadyExistException(long id) {
        super(String.format(FILM_ALREADY_EXIST.message(), id));
    }
}