package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.IdHolder;

import java.util.List;

public interface Storage<T extends IdHolder> {
    T add(T object);

    T update(T object);

    T get(String sql, long id);

    List<T> getList(String sql);
}