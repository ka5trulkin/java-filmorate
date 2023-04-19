package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.IdHolder;

import java.util.Collection;

public interface Service<T extends IdHolder> {
    T add(T object);

    T update(T object);

    T get(long id);

    Collection<T> getList();
}