package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.interfaces.service.GenreService;

import java.util.Collection;

import static ru.yandex.practicum.filmorate.message.GenreLogMessage.*;

@Slf4j
@RestController
@RequestMapping("/genres")
@AllArgsConstructor
public class GenresController {
    private final GenreService service;

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