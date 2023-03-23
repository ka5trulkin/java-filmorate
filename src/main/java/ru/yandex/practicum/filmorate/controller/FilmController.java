package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import javax.validation.Valid;
import java.util.List;

import static ru.yandex.practicum.filmorate.message.FilmLogMessage.*;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController extends AbstractController<Film> {
    @Autowired
    private FilmController(InMemoryFilmStorage repository) {
        super(repository);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film add(@Valid @RequestBody Film film) {
        log.info(REQUEST_ADD_MOVIE.message(), film.getName());
        return super.add(film);
    }

    @Override
    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info(REQUEST_UPDATE_MOVIE.message(), film.getId(), film.getName());
        return super.update(film);
    }

    @Override
    @GetMapping
    public List<Film> getList() {
        log.info(REQUEST_GET_FILM_LIST.message());
        return super.getList();
    }

    @Override
    @GetMapping("/{id}")
    protected Film get(@PathVariable("id") long id) {
        return super.get(id);
    }
}