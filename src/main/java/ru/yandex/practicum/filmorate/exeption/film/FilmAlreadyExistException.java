package ru.yandex.practicum.filmorate.exeption.film;

import ru.yandex.practicum.filmorate.exeption.RequestException;
import ru.yandex.practicum.filmorate.message.ExceptionMessage;

public class FilmAlreadyExistException extends RequestException {
    public FilmAlreadyExistException(long id) {
        super(String.format("Фильм ID:%s уже существует", id));
    }
}