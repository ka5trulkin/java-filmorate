package ru.yandex.practicum.filmorate.exeption.film;

import ru.yandex.practicum.filmorate.exeption.NoDataException;

public class FilmNotFoundException extends NoDataException {
    public FilmNotFoundException(long id) {
        super(String.format("Фильм ID:%s не найден", id));
    }
}