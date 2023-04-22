package ru.yandex.practicum.filmorate.interfaces.service;

public interface Service<T> extends TinyService<T> {
    T add(T object);

    void add(Object... args);

    T update(T object);

//    void delete(Object... args);
}