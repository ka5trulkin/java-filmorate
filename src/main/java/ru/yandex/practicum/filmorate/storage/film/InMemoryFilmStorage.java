package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.FilmInMemory;
import ru.yandex.practicum.filmorate.storage.AbstractStorage;

@Component
public class InMemoryFilmStorage extends AbstractStorage<FilmInMemory> implements FilmStorage {
}