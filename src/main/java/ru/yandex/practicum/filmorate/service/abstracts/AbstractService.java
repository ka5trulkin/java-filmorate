package ru.yandex.practicum.filmorate.service.abstracts;

import lombok.AllArgsConstructor;
import ru.yandex.practicum.filmorate.interfaces.storage.Storage;

import java.util.Collection;

@AllArgsConstructor
public abstract class AbstractService<T> {
    private final Storage<T> storage;

    public T add(T object) {
        return storage.add(object);
    }

    public void add(String sql, Object... args) {
        storage.add(sql, args);
    }

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

    public void delete(String sql, Object... args) {
        storage.delete(sql, args);
    }
}