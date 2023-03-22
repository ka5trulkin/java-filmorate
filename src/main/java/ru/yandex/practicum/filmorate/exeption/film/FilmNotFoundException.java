package ru.yandex.practicum.filmorate.exeption.film;

import ru.yandex.practicum.filmorate.exeption.NoDataException;

import static ru.yandex.practicum.filmorate.message.ExceptionMessage.FILM_NOT_FOUND;

public class FilmNotFoundException extends NoDataException {
    public FilmNotFoundException(long id) {
        super(String.format(FILM_NOT_FOUND.message(), id));
    }
}