package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.interfaces.service.FilmService;

import javax.validation.Valid;
import java.util.Collection;

import static ru.yandex.practicum.filmorate.message.FilmLogMessage.*;

@Slf4j
@RestController
@RequestMapping("/films")
@AllArgsConstructor
public class FilmController {
    private final FilmService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film add(@Valid @RequestBody Film film) {
        log.info(REQUEST_ADD_FILM.message(), film.getName());
        return service.add(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info(REQUEST_UPDATE_FILM.message(), film.getId(), film.getName());
        return service.update(film);
    }

    @GetMapping("/{id}")
    public Film get(@PathVariable("id") long id) {
        log.info(REQUEST_GET_FILM.message(), id);
        return service.get(id);
    }

    @GetMapping
    public Collection<Film> getList() {
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
    public Collection<Film> getPopularList(@RequestParam(defaultValue = "10") long count) {
        log.info(REQUEST_GET_POPULAR_FILM_LIST.message());
        return service.getPopularList(count);
    }
}