package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.model.IdHolder;
import ru.yandex.practicum.filmorate.storage.AbstractStorage;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractController <T extends IdHolder> {
    protected final AbstractStorage<T> repository;

    protected AbstractController(AbstractStorage<T> repository) {
        this.repository = repository;
    }

    protected T add(T object) {
        return repository.add(object);
    }

    protected T update(T object) {
        return repository.update(object);
    }

    protected List<T> getList() {
        return new ArrayList<>(repository.getList());
    }
}