package ru.yandex.practicum.filmorate.service.interfaces;

import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;

import java.util.List;

public interface FilmDao<T extends Film> extends FilmService<T> {
    List<Genre> getGenres();
}
