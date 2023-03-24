package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.AbstractStorage;

@Slf4j
@Component
public class InMemoryFilmStorage extends AbstractStorage<Film> implements FilmStorage {
    @Override
    public Film get(long id) {
        return super.get(id, Film.class);
    }
}