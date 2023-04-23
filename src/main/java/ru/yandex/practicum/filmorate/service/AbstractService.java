package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import ru.yandex.practicum.filmorate.interfaces.storage.Storage;

import java.util.Collection;

@AllArgsConstructor
public abstract class AbstractService<T, S extends Storage<T>> {
    protected final S storage;

    public T add(T object) {
        return storage.add(object);
    }

    public T update(T object) {
        return storage.update(object);
    }

    public T get(Object... args) {
        return storage.get(args);
    }

    public Collection<T> getList() {
        return storage.getList();
    }
}