package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.IdHolder;

import java.util.Collection;

public interface Storage<T extends IdHolder> {
    T add(T object);

    void add(String sql, Object[] args);

    T update(T object);

    void update(String sql, long id);

    T get(String sql, long id);

    Collection<T> getList(String sql);

    Collection<T> getList(String sql, Object[] args);

    void delete(String sql, Object[] args);
}