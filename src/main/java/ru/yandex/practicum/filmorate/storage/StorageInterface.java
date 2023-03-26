package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.IdHolder;

import java.util.List;

public interface StorageInterface<T extends IdHolder> {
    T add(T film);

    T update(T film);

    List<T> getList();

    T get(long id);
}