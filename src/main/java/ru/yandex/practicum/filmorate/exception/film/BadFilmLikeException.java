package ru.yandex.practicum.filmorate.exception.film;

import ru.yandex.practicum.filmorate.exception.RequestException;

import static ru.yandex.practicum.filmorate.message.ExceptionMessage.BAD_FILM_LIKE;

public class BadFilmLikeException extends RequestException {
    public BadFilmLikeException(long id, long userId) {
        super(String.format(BAD_FILM_LIKE.message(), id, userId));
    }
}