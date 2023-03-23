package ru.yandex.practicum.filmorate.exeption.film;

import ru.yandex.practicum.filmorate.exeption.NoDataException;

import static ru.yandex.practicum.filmorate.message.ExceptionMessage.FILM_LIKE_NOT_FOUND;

public class FilmLikeNotFoundException extends NoDataException {
    public FilmLikeNotFoundException(long id, long userId) {
        super(String.format(FILM_LIKE_NOT_FOUND.message(), id, userId));
    }
}