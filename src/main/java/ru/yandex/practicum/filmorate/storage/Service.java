package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.IdHolder;

import java.util.List;

public interface Service<T extends IdHolder> {
    T add(T object);

    T update(T object);

    T get(long id);

    List<T> getList();
}