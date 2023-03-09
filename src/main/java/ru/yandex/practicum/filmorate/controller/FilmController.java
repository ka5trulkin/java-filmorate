package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController extends AbstractController<Film> {
    @Autowired
    private FilmController(FilmRepository repository) {
        super(repository);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film add(@Valid @RequestBody Film film) {
        log.info("Запрос на добавление фильма Name: {}", film.getName());
        return super.add(film);
    }

    @Override
    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("Запрос на обновление фильма ID:{} Name: {}", film.getId(), film.getName());
        return super.update(film);
    }

    @Override
    @GetMapping
    public List<Film> getList() {
        log.info("Получение списка фильмов");
        return super.getList();
    }
}