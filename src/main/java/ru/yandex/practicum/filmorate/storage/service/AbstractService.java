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

    public void add(String sql, long id, long otherId) {
        storage.add(sql, id, otherId);
    }

    @Override
    public T update(T object) {
        return storage.update(object);
    }

    public T get(String sql, long id) {
        return storage.get(sql, id);
    }

    public Collection<T> getList(String sql) {
        return storage.getList(sql);
    }

    @Override
    public void delete(String sql, long id, long friendId) {
        storage.delete(sql, id, friendId);
    }
}