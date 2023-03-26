package ru.yandex.practicum.filmorate.exeption.film;

import ru.yandex.practicum.filmorate.exeption.NotFoundException;

import static ru.yandex.practicum.filmorate.message.ExceptionMessage.FILM_NOT_FOUND;

public class FilmNotFoundException extends NotFoundException {
    public FilmNotFoundException(long id) {
        super(String.format(FILM_NOT_FOUND.message(), id));
    }
}