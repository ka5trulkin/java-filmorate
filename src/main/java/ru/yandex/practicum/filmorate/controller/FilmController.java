package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    @Autowired
    private FilmRepository repository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film add(@Valid @RequestBody Film film) {
        return repository.addNewFilm(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        return repository.updateFilm(film);
    }

    @GetMapping
    public List<Film> getFilmList() {
        log.info("Получение списка фильмов.");
        return new ArrayList<>(repository.getFilmList());
    }
}