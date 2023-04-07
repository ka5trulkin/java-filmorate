package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.film.FilmInMemory;
import ru.yandex.practicum.filmorate.service.FilmInMemoryService;
import ru.yandex.practicum.filmorate.storage.dao.FilmDaoStorage;

import javax.validation.Valid;
import java.util.List;

import static ru.yandex.practicum.filmorate.message.FilmLogMessage.*;

@RestController
@RequestMapping("/in-memory-films")
@Slf4j
public class FilmInMemoryController {
    private final FilmInMemoryService service;

    @Autowired
    public FilmInMemoryController(FilmInMemoryService service, FilmDaoStorage filmDao) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FilmInMemory add(@Valid @RequestBody FilmInMemory film) {
        log.info(REQUEST_ADD_FILM.message(), film.getName());
        return service.add(film);
    }

    @PutMapping
    public FilmInMemory update(@Valid @RequestBody FilmInMemory film) {
        log.info(REQUEST_UPDATE_FILM.message(), film.getId(), film.getName());
        return service.update(film);
    }

    @GetMapping("/{id}")
    protected FilmInMemory get(@PathVariable("id") long id) {
        log.info(REQUEST_GET_FILM.message(), id);
        return service.get(id);
    }

    @GetMapping
    public List<FilmInMemory> getList() {
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
    public List<FilmInMemory> getPopularList(@RequestParam(defaultValue = "10") long count) {
        log.info(REQUEST_GET_POPULAR_FILM_LIST.message());
        return service.getPopularList(count);
    }
}