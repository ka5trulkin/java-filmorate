package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exeption.NoDataException;
import ru.yandex.practicum.filmorate.exeption.RequestException;
import ru.yandex.practicum.filmorate.model.IdHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.yandex.practicum.filmorate.exeption.InfoMessage.OBJECT_ALREADY_EXISTS;
import static ru.yandex.practicum.filmorate.exeption.InfoMessage.OBJECT_NOT_FOUND;

public abstract class AbstractStorage<T extends IdHolder> {
    protected final Map<Long, T> data = new HashMap<>();
    protected long idCounter;

    private boolean isContain(T object) {
        return data.containsKey(object.getId());
    }

    public T add(T object) {
        if (isContain(object)) {
            throw new RequestException(OBJECT_ALREADY_EXISTS.message() + object);
        }
        object.setId(++idCounter);
        data.put(object.getId(), object);
        return data.get(object.getId());
    }

    public T update(T object) {
        if (!isContain(object)) {
            throw new NoDataException(OBJECT_NOT_FOUND.message() + object);
        }
        data.put(object.getId(), object);
        return data.get(object.getId());
    }

    public T get(long id) {
        if (!data.containsKey(id)) {
            throw new RuntimeException();
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