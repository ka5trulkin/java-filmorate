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

public abstract class AbstractRepository {
    protected final Map<Integer, IdHolder> data = new HashMap<>();
    protected int idCounter;

    private boolean isContain(IdHolder object) {
        return data.containsKey(object.getId());
    }

    public IdHolder add(IdHolder object) {
        if (isContain(object)) {
            throw new RequestException(OBJECT_ALREADY_EXISTS.getMessage());
        }
        object.setId(++idCounter);
        data.put(object.getId(), object);
        return data.get(object.getId());
    }

    public IdHolder update(IdHolder object) {
        if (!isContain(object)) {
            throw new NoDataException(OBJECT_NOT_FOUND.getMessage());
        }
        data.put(object.getId(), object);
        return data.get(object.getId());
    }

    public List<IdHolder> getList() {
        return new ArrayList<>(data.values());
    }

    public void deleteAll() {
        data.clear();
        idCounter = 0;
    }
}