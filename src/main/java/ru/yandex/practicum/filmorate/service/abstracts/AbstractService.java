package ru.yandex.practicum.filmorate.service.abstracts;

import ru.yandex.practicum.filmorate.interfaces.storage.Storage;

public abstract class AbstractService<T, S extends Storage<T>> extends AbstractTinyService<T,S> {
    public AbstractService(S storage) {
        super(storage);
    }

    public T add(T object) {
        return storage.add(object);
    }

    public T update(T object) {
        return storage.update(object);
    }
}