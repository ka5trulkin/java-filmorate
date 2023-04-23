package ru.yandex.practicum.filmorate.interfaces.service;

import java.util.Collection;

public interface Service<T> {
    T add(T object);

    T update(T object);

    T get(long id);

    Collection<T> getList();
}