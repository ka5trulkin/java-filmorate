package ru.yandex.practicum.filmorate.exception.film;

import ru.yandex.practicum.filmorate.exception.RequestException;

import static ru.yandex.practicum.filmorate.message.ExceptionMessage.FILM_LIKE_ALREADY_EXIST;

public class FilmLikeAlreadyExistException extends RequestException {
    public FilmLikeAlreadyExistException(long id, long userId) {
        super(String.format(FILM_LIKE_ALREADY_EXIST.message(), id, userId));
    }
}