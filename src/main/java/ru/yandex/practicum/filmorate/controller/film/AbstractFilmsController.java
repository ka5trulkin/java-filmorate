package ru.yandex.practicum.filmorate.controller.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dao.interfaces.FilmDao;
import ru.yandex.practicum.filmorate.model.film.AbstractFilm;

import javax.validation.Valid;
import java.util.List;

import static ru.yandex.practicum.filmorate.message.FilmLogMessage.*;

@Slf4j
public abstract class AbstractFilmsController<T extends AbstractFilm> {
    protected final FilmDao<T> service;

    @Autowired
    protected AbstractFilmsController(FilmDao<T> filmDao) {
        this.service = filmDao;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public T add(@Valid @RequestBody T film) {
        log.info(REQUEST_ADD_FILM.message(), film.getName());
        return service.add(film);
    }

    @PutMapping
    public T update(@Valid @RequestBody T film) {
        log.info(REQUEST_UPDATE_FILM.message(), film.getId(), film.getName());
        return service.update(film);
    }

    @GetMapping("/{id}")
    protected T get(@PathVariable("id") long id) {
        log.info(REQUEST_GET_FILM.message(), id);
        return service.get(id);
    }

    @GetMapping
    public List<T> getList() {
        log.info(REQUEST_GET_FILM_LIST.message());
        return service.getList();
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable long id, @PathVariable long userId) {
        log.info(REQUEST_ADD_FILM_LIKE.message(), id, userId);
        service.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable long id, @PathVariable long userId) {
        log.info(REQUEST_REMOVE_FILM_LIKE_.message(), id, userId);
        service.removeLike(id, userId);
    }

    @GetMapping("/popular")
    public List<T> getPopularList(@RequestParam(defaultValue = "10") long count) {
        log.info(REQUEST_GET_POPULAR_FILM_LIST.message());
        return service.getPopularList(count);
    }
}