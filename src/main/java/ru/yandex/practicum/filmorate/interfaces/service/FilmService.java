package ru.yandex.practicum.filmorate.interfaces.service;

import ru.yandex.practicum.filmorate.model.film.Film;

import java.util.Collection;

public interface FilmService extends Service<Film> {
    void addLike(long id, long userId);

    void removeLike(long id, long userId);

    Collection<Film> getPopularList(int count);
}