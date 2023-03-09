package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.model.IdHolder;
import ru.yandex.practicum.filmorate.repository.AbstractRepository;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractController {
    protected final AbstractRepository repository;

    protected AbstractController(AbstractRepository repository) {
        this.repository = repository;
    }

    protected IdHolder add(IdHolder object) {
        return repository.add(object);
    }

    protected IdHolder update(IdHolder object) {
        return repository.update(object);
    }

    protected List<IdHolder> getList() {
        return new ArrayList<>(repository.getList());
    }
}