package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.repository.CustomRepository;

public abstract class CustomController {
    protected final CustomRepository repository;

    protected CustomController(CustomRepository repository) {
        this.repository = repository;
    }
}
