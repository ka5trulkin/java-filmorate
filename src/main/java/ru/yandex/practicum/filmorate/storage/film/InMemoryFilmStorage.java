package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.NoDataException;
import ru.yandex.practicum.filmorate.exeption.film.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.exeption.RequestException;
import ru.yandex.practicum.filmorate.exeption.film.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.AbstractStorage;

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
        try {
            super.update(film);
        } catch (NoDataException e) {
            throw new FilmNotFoundException(film.getId());
        }
        log.info(FILM_UPDATED.message(), film.getId(), film.getName());
        return film;
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

    @Override
    public Film get(long id) {
        Film film;
        try {
            film = super.get(id);
        } catch (NoDataException e) {
            throw new FilmNotFoundException(id);
        }
        log.info(FILM_GET.message(), id);
        return film;
    }
}