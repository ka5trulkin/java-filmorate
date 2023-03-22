package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.exeption.RequestException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

import static ru.yandex.practicum.filmorate.message.LogMessage.*;

@Slf4j
@Component
public class InMemoryFilmStorage extends AbstractStorage<Film> implements FilmStorage {
    @Override
    public Film add(Film film) {
        try {
            super.add(film);
        } catch (RequestException e) {
            throw new FilmAlreadyExistException(film.getId());
        }
        log.info(FILM_ADDED.message(), film.getName());
        return film;
    }

    @Override
    public Film update(Film film) {
        log.info(FILM_UPDATED.message(), film.getId(), film.getName());
        return super.update(film);
    }

    @Override
    public List<Film> getList() {
        log.info(GET_FILM_LIST.message());
        return super.getList();
    }

    @Override
    public void clear() {
        log.info(REPOSITORY_CLEAN.message());
        super.clear();
    }
}