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

import static ru.yandex.practicum.filmorate.message.FilmLogMessage.*;

@Slf4j
@Component
public class InMemoryFilmStorage extends AbstractStorage<Film> implements FilmStorage {
    @Override
    public Film add(Film film) {
        Film returnedFilm;
        try {
            returnedFilm = super.add(film);
        } catch (RequestException e) {
            throw new FilmAlreadyExistException(film.getId());
        }
        return returnedFilm;
    }

    @Override
    public Film update(Film film) {
        Film returnedFilm;
        try {
            returnedFilm = super.update(film);
        } catch (NoDataException e) {
            throw new FilmNotFoundException(film.getId());
        }
        return returnedFilm;
    }

    @Override
    public Film get(long id) {
        Film returnedFilm;
        try {
            returnedFilm = super.get(id);
        } catch (NoDataException e) {
            throw new FilmNotFoundException(id);
        }
        return returnedFilm;
    }

    @Override
    public List<Film> getList() {
        return super.getList();
    }

    @Override
    public void clear() {
        log.info(FILM_STORAGE_CLEAN.message());
        super.clear();
    }
}