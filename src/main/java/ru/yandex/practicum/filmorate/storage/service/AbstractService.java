package ru.yandex.practicum.filmorate.storage.service;

import lombok.AllArgsConstructor;
import ru.yandex.practicum.filmorate.model.IdHolder;
import ru.yandex.practicum.filmorate.storage.Service;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.Collection;

@AllArgsConstructor
public abstract class AbstractService<T extends IdHolder> implements Service<T> {
    private final Storage<T> storage;

    @Override
    public T add(T object) {
        return storage.add(object);
    }

    @Override
    public void add(String sql, Object... args) {
        storage.add(sql, args);
    }

    @Override
    public T update(T object) {
        return storage.update(object);
    }

    public void update(String sql, Object... args) {
        storage.update(sql, args);
    }

    public T get(String sql, Object... args) {
        return storage.get(sql, args);
    }

    public Collection<T> getList(String sql) {
        return storage.getList(sql);
    }

    public Collection<T> getList(String sql, Object... args) {
        return storage.getList(sql, args);
    }

    @Override
    public void delete(String sql, Object... args) {
        storage.delete(sql, args);
    }
}