package ru.yandex.practicum.filmorate.service.interfaces;

import ru.yandex.practicum.filmorate.model.film.Film;

public interface FilmDao<T extends Film> extends FilmService<T> {
    void testMethod();
}
