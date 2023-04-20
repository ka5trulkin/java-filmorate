package ru.yandex.practicum.filmorate.storage;

public interface Service<T> extends TinyService<T> {
    T add(T object);

    void add(String sql, Object... args);

    T update(T object);

    void delete(String sql, Object... args);
}