package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.service.interfaces.Dao;
import ru.yandex.practicum.filmorate.exception.object.ObjectAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.object.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.IdHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractStorage<T extends IdHolder> implements Dao<T> {
    private final Map<Long, T> data = new HashMap<>();
    private long idCounter;

    private boolean isContain(T object) {
        return data.containsKey(object.getId());
    }

    public T add(T object) {
        if (isContain(object)) {
            throw new ObjectAlreadyExistException(object.getId());
        }
        object.setId(++idCounter);
        data.put(object.getId(), object);
        return data.get(object.getId());
    }

    public T update(T object) {
        if (!isContain(object)) {
            throw new ObjectNotFoundException(object.getId());
        }
        data.put(object.getId(), object);
        return data.get(object.getId());
    }

    public T get(long id) {
        if (!data.containsKey(id)) {
            throw new ObjectNotFoundException(id);
        }
        return data.get(id);
    }

    public List<T> getList() {
        return new ArrayList<>(data.values());
    }

    public void clear() {
        data.clear();
        idCounter = 0;
    }
}