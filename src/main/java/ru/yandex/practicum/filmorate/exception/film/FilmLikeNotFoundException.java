package ru.yandex.practicum.filmorate.exception.film;

import ru.yandex.practicum.filmorate.exception.NotFoundException;

import static ru.yandex.practicum.filmorate.message.ExceptionMessage.FILM_LIKE_NOT_FOUND;

public class FilmLikeNotFoundException extends NotFoundException {
    public FilmLikeNotFoundException(long id, long userId) {
        super(String.format(FILM_LIKE_NOT_FOUND.message(), id, userId));
    }
}