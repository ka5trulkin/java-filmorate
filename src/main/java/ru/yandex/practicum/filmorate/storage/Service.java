package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.IdHolder;

import java.util.Collection;

public interface Service<T extends IdHolder> {
    T add(T object);

    void add(String sql, Object... args);

    T update(T object);

    T get(long id);

    Collection<T> getList();

    void delete(String sql, Object... args);
}