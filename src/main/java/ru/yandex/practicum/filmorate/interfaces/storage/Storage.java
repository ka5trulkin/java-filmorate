package ru.yandex.practicum.filmorate.interfaces.storage;

public interface Storage<T> extends TinyStorage<T> {
    T add(T object);

    T update(T object);

    int update(String sql, Object... args);
}