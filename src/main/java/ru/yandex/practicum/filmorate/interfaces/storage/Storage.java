package ru.yandex.practicum.filmorate.interfaces.storage;

import java.util.Collection;

public interface Storage<T> {
    T add(T object);

    T update(T object);

    int update(String sql, Object... args);

    T get(Object... args);

    Collection<T> getList();
}