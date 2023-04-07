package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dao.interfaces.FilmDao;
import ru.yandex.practicum.filmorate.model.film.Film;

import javax.validation.Valid;
import java.util.List;

import static ru.yandex.practicum.filmorate.message.FilmLogMessage.*;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmsController {
    private final FilmDao<Film> filmDao;

    @Autowired
    public FilmsController(@Qualifier("filmDaoImplement") FilmDao<Film> filmDao) {
        this.filmDao = filmDao;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film add(@Valid @RequestBody Film film) {
        log.info(REQUEST_ADD_FILM.message(), film.getName());
        return filmDao.add(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info(REQUEST_UPDATE_FILM.message(), film.getId(), film.getName());
        return filmDao.update(film);
    }

    @GetMapping("/{id}")
    protected Film get(@PathVariable("id") long id) {
        log.info(REQUEST_GET_FILM.message(), id);
        return filmDao.get(id);
    }

    @GetMapping
    public List<Film> getList() {
        log.info(REQUEST_GET_FILM_LIST.message());
        return filmDao.getList();
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable long id, @PathVariable long userId) {
        log.info(REQUEST_ADD_FILM_LIKE.message(), id, userId);
        filmDao.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable long id, @PathVariable long userId) {
        log.info(REQUEST_REMOVE_FILM_LIKE_.message(), id, userId);
        filmDao.removeLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularList(@RequestParam(defaultValue = "10") long count) {
        log.info(REQUEST_GET_POPULAR_FILM_LIST.message());
        return filmDao.getPopularList(count);
    }
}