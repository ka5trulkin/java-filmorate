package ru.yandex.practicum.filmorate.interfaces.storage;

import ru.yandex.practicum.filmorate.model.film.Film;

import java.util.Collection;

public interface FilmRepStorage<T> extends Storage<T> {
    void addLike(long filmId, long userId);

    void removeLike(long id, long userId);

    Collection<Film> getPopularList(int count);
}
