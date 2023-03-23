package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NoDataException;
import ru.yandex.practicum.filmorate.exeption.RequestException;
import ru.yandex.practicum.filmorate.exeption.film.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.exeption.film.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;

import static ru.yandex.practicum.filmorate.message.FilmLogMessage.*;

@Slf4j
@Service
public class FilmService {
    @Autowired
    FilmStorage storage;

    public Film add(Film film) {
        log.info(FILM_ADDED.message(), film.getName());
        return storage.add(film);
    }

    public Film update(Film film) {
        log.info(FILM_UPDATED.message(), film.getId(), film.getName());
        return storage.update(film);
    }

    public Film get(long id) {
        log.info(GET_FILM.message(), id);
        return storage.get(id);
    }

    public List<Film> getList() {
        log.info(GET_FILM_LIST.message());
        return storage.getList();
    }
}