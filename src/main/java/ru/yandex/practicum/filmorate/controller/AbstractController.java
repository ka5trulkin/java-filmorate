package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.model.IdHolder;
import ru.yandex.practicum.filmorate.repository.AbstractRepository;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractController <T extends IdHolder> {
    protected final AbstractRepository repository;

    protected AbstractController(AbstractRepository repository) {
        this.repository = repository;
    }

    protected IdHolder add(T object) {
        return repository.add(object);
    }

    protected IdHolder update(T object) {
        return repository.update(object);
    }

    protected List<IdHolder> getList() {
        return new ArrayList<>(repository.getList());
    }
}