package ru.yandex.practicum.filmorate.dao.interfaces;

import ru.yandex.practicum.filmorate.model.film.AbstractFilm;

import java.util.List;

public interface FilmDao<T extends AbstractFilm> extends Dao<T> {
    void addLike(long id, long userId);

    void removeLike(long id, long userId);

    List<T> getPopularList(long count);
}