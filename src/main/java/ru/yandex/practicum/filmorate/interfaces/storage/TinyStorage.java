package ru.yandex.practicum.filmorate.interfaces.storage;

import java.util.Collection;

public interface TinyStorage<T> {
    T get(Object... args);

    Collection<T> getList();
}