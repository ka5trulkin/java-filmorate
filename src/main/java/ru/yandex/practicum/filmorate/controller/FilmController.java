package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeption.DataUpdateException;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmRepository;

import javax.naming.Binding;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    @Autowired
    private FilmRepository repository;

//    private boolean isValid(Film film) {
//        final int maxDescriptionLength = 200;
//        final LocalDate firstFilmDate = LocalDate.of(1895, 12, 28);
//        return film.getName().isBlank()
//                || film.getDescription().length() > maxDescriptionLength
//                || film.getReleaseDate().isBefore(firstFilmDate)
//                || film.getReleaseDate().isAfter(LocalDate.now())
//                || film.getDuration() <= 0;
//    }

    private void validationError(Film film) {
        log.warn("Переданы некорректные данные о фильме: " + film);
        throw new ValidationException("Данные о фильме не соответствует установленным критериям.");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film add(@Valid @RequestBody Film film) {
        if (repository.isContains(film)) {
            log.warn("Ошибка добавления. Фильм с ID: " + film.getId() + " уже существует.");
            throw new ValidationException("Фильм с ID: " + film.getId() + " уже существует.");
        }
//        if (isValid(film)) {
//            validationError(film);
//        }
        log.info("Фильм " + film.getName() + " добавлен.");
        return repository.addNewFilm(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
//        if (isValid(film)) {
//            validationError(film);
//        }

        if (!repository.isContains(film)) {
            log.warn("Ошибка обновления. Фильма с ID: " + film.getId() + " не существует.");
            throw new DataUpdateException("Фильма с ID: " + film.getId() + " не существует.");
        }
        log.info("Фильм с ID: " + film.getId() + " обновлен.");
        return repository.updateFilm(film);
    }

    @GetMapping
    public List<Film> getFilmList() {
        log.info("Получение списка фильмов.");
        return new ArrayList<>(repository.getFilmList());
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(ValidationException.class)
//    public String validationException(ValidationException exception) throws JsonProcessingException {
//        return new ObjectMapper().writeValueAsString(exception.getMessage());
//    }
//
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(DataUpdateException.class)
//    public String updateException(DataUpdateException exception) throws JsonProcessingException {
//        return new ObjectMapper().writeValueAsString(exception.getMessage());
//    }
}