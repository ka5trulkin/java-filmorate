package ru.yandex.practicum.filmorate.storage;

import java.util.Collection;

public interface TinyService<T> {
    T get(long id);

    Collection<T> getList();
}