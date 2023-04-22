package ru.yandex.practicum.filmorate.service.abstracts;

import ru.yandex.practicum.filmorate.interfaces.storage.Storage;

import java.util.Collection;

//@AllArgsConstructor
public abstract class AbstractService<T, S extends Storage<T>> {
    protected final S storage;

    public AbstractService(S storage) {
        this.storage = storage;
    }

    public T add(T object) {
        return storage.add(object);
    }

    public void add(Object... args) {
        storage.add(args);
    }

    public T update(T object) {
        return storage.update(object);
    }

    public void update(String sql, Object... args) {
        storage.update(sql, args);
    }

    public T get(Object... args) {
        return storage.get(args);
    }

    public Collection<T> getList() {
        return storage.getList();
    }

//    public Collection<T> getList(String sql, Object... args) {
//        return storage.getList(sql, args);
//    }

//    public void delete(Object... args) {
//        storage.delete(args);
//    }
}