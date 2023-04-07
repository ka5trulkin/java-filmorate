package ru.yandex.practicum.filmorate.storage.memory;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.FilmInMemory;

@Component
public class FilmInMemoryStorage extends AbstractInMemoryStorage<FilmInMemory> {
}