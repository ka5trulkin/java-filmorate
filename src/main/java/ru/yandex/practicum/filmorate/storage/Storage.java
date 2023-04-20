package ru.yandex.practicum.filmorate.storage;

import java.util.Collection;

public interface Storage<T> extends TinyStorage<T> {
    T add(T object);

    void add(String sql, Object... args);

    T update(T object);

    void update(String sql, Object... args);

    Collection<T> getList(String sql, Object... args);

    void delete(String sql, Object... args);
}