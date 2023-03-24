package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exeption.film.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.exeption.film.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exeption.user.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exeption.user.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.IdHolder;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractStorage<T extends IdHolder> {
    protected final Map<Long, T> data = new HashMap<>();
    protected long idCounter;

    private boolean isContain(T object) {
        return data.containsKey(object.getId());
    }

    private void getAlreadyExistException(T object) {
        if (object.getClass() == User.class) {
            throw new UserAlreadyExistException(object.getId());
        }
        if (object.getClass() == Film.class) {
            throw new FilmAlreadyExistException(object.getId());
        }
    }

    private void getNotFoundException(T object) {
        if (object.getClass() == User.class) {
            throw new UserNotFoundException(object.getId());
        }
        if (object.getClass() == Film.class) {
            throw new FilmNotFoundException(object.getId());
        }
    }

    private void getNotFoundException(long id, Class<T> objectClass) {
        if (objectClass == User.class) {
            throw new UserNotFoundException(id);
        }
        if (objectClass == Film.class) {
            throw new FilmNotFoundException(id);
        }
    }

    public T add(T object) {
        if (isContain(object)) {
            getAlreadyExistException(object);
        }
        object.setId(++idCounter);
        data.put(object.getId(), object);
        return data.get(object.getId());
    }

    public T update(T object) {
        if (!isContain(object)) {
            getNotFoundException(object);
        }
        data.put(object.getId(), object);
        return data.get(object.getId());
    }

    public T get(long id, Class<T> objectClass) {
        if (!data.containsKey(id)) {
            getNotFoundException(id, objectClass);
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