package ru.yandex.practicum.filmorate.storage.service;

import lombok.AllArgsConstructor;
import ru.yandex.practicum.filmorate.storage.TinyStorage;

import java.util.Collection;

@AllArgsConstructor
public abstract class AbstractTinyService<T>{
    private final TinyStorage<T> storage;

    public T get(String sql, Object... args) {
        return storage.get(sql, args);
    }

    public Collection<T> getList(String sql) {
        return storage.getList(sql);
    }
}