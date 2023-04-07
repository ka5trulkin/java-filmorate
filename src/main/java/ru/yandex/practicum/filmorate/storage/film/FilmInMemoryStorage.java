package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.AbstractInMemoryStorage;

@Component
public class FilmInMemoryStorage extends AbstractInMemoryStorage<ru.yandex.practicum.filmorate.model.film.FilmInMemory> {
}