package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.IdHolder;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.List;

public abstract class AbstractService<T extends IdHolder> {
    private final Storage<T> storage;

    protected AbstractService(Storage<T> storage) {
        this.storage = storage;
    }

    public T add(T object) {
        return storage.add(object);
    }

    public T update(T object) {
        return storage.update(object);
    }

    public T get(long id) {
        return storage.get(id);
    }

    public List<T> getList() {
        return storage.getList();
    }
}