package ru.yandex.practicum.filmorate.service.interfaces;

import ru.yandex.practicum.filmorate.model.film.AbstractFilm;

public interface FilmDao<T extends AbstractFilm> extends FilmService<T> {
    void testMethod();
}
