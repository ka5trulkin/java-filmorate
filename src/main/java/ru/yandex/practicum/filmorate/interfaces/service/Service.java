package ru.yandex.practicum.filmorate.interfaces.service;

public interface Service<T> extends TinyService<T> {
    T add(T object);

    T update(T object);
}