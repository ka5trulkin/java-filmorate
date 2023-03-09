package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.exeption.NoDataException;
import ru.yandex.practicum.filmorate.exeption.RequestException;
import ru.yandex.practicum.filmorate.model.IdHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.yandex.practicum.filmorate.exeption.ExceptionMessage.OBJECT_ALREADY_EXISTS;
import static ru.yandex.practicum.filmorate.exeption.ExceptionMessage.OBJECT_NOT_FOUND;

public abstract class AbstractRepository<T extends IdHolder> {
    protected final Map<Integer, T> data = new HashMap<>();
    protected int idCounter;

    private boolean isContain(T object) {
        return data.containsKey(object.getId());
    }

    public T add(T object) {
        if (isContain(object)) {
            throw new RequestException(OBJECT_ALREADY_EXISTS.getMessage() + object);
        }
        object.setId(++idCounter);
        data.put(object.getId(), object);
        return data.get(object.getId());
    }

    public T update(T object) {
        if (!isContain(object)) {
            throw new NoDataException(OBJECT_NOT_FOUND.getMessage() + object);
        }
        data.put(object.getId(), object);
        return data.get(object.getId());
    }

    public List<T> getList() {
        return new ArrayList<>(data.values());
    }

    public void deleteAll() {
        data.clear();
        idCounter = 0;
    }
}