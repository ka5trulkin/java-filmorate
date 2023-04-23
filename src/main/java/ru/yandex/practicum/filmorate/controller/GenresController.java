package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.interfaces.service.GenreService;
import ru.yandex.practicum.filmorate.model.film.Genre;

import javax.validation.Valid;
import java.util.Collection;

import static ru.yandex.practicum.filmorate.message.GenreLogMessage.*;

@Slf4j
@RestController
@RequestMapping("/genres")
@AllArgsConstructor
public class GenresController {
    private final GenreService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Genre add(@Valid @RequestBody Genre genre) {
        log.info(REQUEST_ADD_GENRE.message(), genre.getId(), genre.getName());
        return service.add(genre);
    }

    @PutMapping
    public Genre update(@Valid @RequestBody Genre genre) {
        log.info(REQUEST_UPDATE_GENRE.message(), genre.getId(), genre.getName());
        return service.update(genre);
    }

    @GetMapping("/{id}")
    public Genre get(@PathVariable("id") long id) {
        log.info(REQUEST_GET_GENRE.message(), id);
        return service.get(id);
    }

    @GetMapping
    public Collection<Genre> getList() {
        log.info(REQUEST_GET_GENRE_LIST.message());
        return service.getList();
    }
}