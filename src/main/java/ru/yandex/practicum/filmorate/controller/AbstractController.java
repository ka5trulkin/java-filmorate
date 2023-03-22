package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.model.IdHolder;
import ru.yandex.practicum.filmorate.storage.AbstractRepository;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractController <T extends IdHolder> {
    protected final AbstractRepository<T> repository;

    protected AbstractController(AbstractRepository<T> repository) {
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