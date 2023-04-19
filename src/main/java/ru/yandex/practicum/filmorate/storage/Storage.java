package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.IdHolder;

import java.util.Collection;

public interface Storage<T extends IdHolder> {
    T add(T object);

    void add(String sql, long id, long friendId);

    T update(T object);

    T get(String sql, long id);

    Collection<T> getList(String sql);
}