package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.IdHolder;
import ru.yandex.practicum.filmorate.repository.FilmRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController extends CustomController {
    @Autowired
    private FilmController(FilmRepository repository) {
        super(repository);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IdHolder add(@Valid @RequestBody Film film) {
        log.info("Запрос на добавление фильма. Name: {}", film.getName());
        return repository.add(film);
    }

    @PutMapping
    public IdHolder update(@Valid @RequestBody Film film) {
        log.info("Запрос на обновление фильма. Name: {}", film.getName());
        return repository.update(film);
    }

    @GetMapping
    public List<IdHolder> getFilmList() {
        log.info("Получение списка фильмов");
        return new ArrayList<>(repository.getList());
    }
}