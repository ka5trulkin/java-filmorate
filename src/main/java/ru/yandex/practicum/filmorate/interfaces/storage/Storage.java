package ru.yandex.practicum.filmorate.interfaces.storage;

public interface Storage<T> extends TinyStorage<T> {
    T add(T object);

    void add(Object... args);

    T update(T object);

    int update(String sql, Object... args);

//    Collection<T> getList(String sql, Object... args);

//    void delete(Object... args);
}