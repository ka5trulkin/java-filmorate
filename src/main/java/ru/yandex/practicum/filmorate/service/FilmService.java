package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

import static ru.yandex.practicum.filmorate.message.FilmLogMessage.*;

@Slf4j
@Service
public class FilmService {
    @Autowired
    FilmStorage filmStorage;
    @Autowired
    UserStorage userStorage;

    public Film add(Film film) {
        log.info(FILM_ADDED.message(), film.getName());
        return filmStorage.add(film);
    }

    public Film update(Film film) {
        log.info(FILM_UPDATED.message(), film.getId(), film.getName());
        return filmStorage.update(film);
    }

    public Film get(long id) {
        log.info(GET_FILM.message(), id);
        return filmStorage.get(id);
    }

    public List<Film> getList() {
        log.info(GET_FILM_LIST.message());
        return filmStorage.getList();
    }

    public void addLike(long id, long userId) {
        userStorage.get(userId);
        filmStorage.get(id).getLikes().add(userId);
        log.info(FILM_LIKE_ADDED.message(), id, userId);
    }
}