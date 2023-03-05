package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final Map<Integer, Film> filmDatabase = new HashMap<>();
    private int idCounter;

    private boolean isValid(Film film) {
        return film.getName().isBlank()
                || film.getDescription().length() > 200
                || film.getReleaseDate().isBefore(LocalDate.of(1895, 12 , 28))
                || film.getDuration().isNegative();
    }

    @PostMapping
    public Film add(@RequestBody Film film) {
        if (filmDatabase.containsKey(film.getId())) {
            log.warn("Ошибка добавления. Фильм с " + film.getId() + " ID уже существует.");
            throw new ValidationException("Фильм с " + film.getId() + " ID уже существует.");
        }
        if (isValid(film)) {
            log.warn("Переданы некорректные данные о фильме: " + film);
            throw new ValidationException("Данные о фильме не соответствует установленным критериям.");
        }
        film.setId(++idCounter);
        filmDatabase.put(film.getId(), film);
        log.info("Фильм " + film.getId() + " ID добавлен.");
        return filmDatabase.get(film.getId());
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        if (isValid(film)) {
            log.warn("Переданы некорректные данные о фильме: " + film);
            throw new ValidationException("Данные о фильме не соответствует установленным критериям.");
        }
        if (!filmDatabase.containsKey(film.getId())) {
            log.warn("Ошибка обновления. Фильма с " + film.getId() + " ID не существует.");
            throw new ValidationException("Фильма с " + film.getId() + " ID не существует.");
        }
        filmDatabase.put(film.getId(), film);
        log.info("Фильм " + film.getId() + " ID обновлен.");
        return filmDatabase.get(film.getId());
    }

    @GetMapping
    public List<Film> getFilmList() {
        log.info("Получение списка фильмов.");
        return new ArrayList<>(filmDatabase.values());
    }
}