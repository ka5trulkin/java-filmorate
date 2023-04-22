package ru.yandex.practicum.filmorate.service.abstracts;

import lombok.AllArgsConstructor;
import ru.yandex.practicum.filmorate.interfaces.storage.TinyStorage;

import java.util.Collection;

@AllArgsConstructor
public abstract class AbstractTinyService<T, S extends TinyStorage<T>> {
    protected final S storage;

    public T get(Object... args) {
        return storage.get(args);
    }

    public Collection<T> getList() {
        return storage.getList();
    }
}