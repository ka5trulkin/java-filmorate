package ru.yandex.practicum.filmorate.storage.service;

import lombok.AllArgsConstructor;
import ru.yandex.practicum.filmorate.model.IdHolder;
import ru.yandex.practicum.filmorate.storage.Service;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.Collection;

@AllArgsConstructor
public abstract class AbstractService<T extends IdHolder> implements Service<T> {
    protected final Storage<T> storage;

    @Override
    public T add(T object) {
        return storage.add(object);
    }

    @Override
    public void add(String sql, Object[] args) {
        storage.add(sql, args);
    }

    @Override
    public T update(T object) {
        return storage.update(object);
    }

    public void update(String sql, long id) {
        storage.update(sql, id);
    }

    public T get(String sql, long id) {
        return storage.get(sql, id);
    }

    public Collection<T> getList(String sql) {
        return storage.getList(sql);
    }

    public Collection<T> getList(String sql, Object[] args) {
        return storage.getList(sql, args);
    }

    @Override
    public void delete(String sql, Object[] args) {
        storage.delete(sql, args);
    }
}