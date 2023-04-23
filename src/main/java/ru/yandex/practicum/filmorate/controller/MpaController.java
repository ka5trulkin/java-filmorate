package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.interfaces.service.MpaService;
import ru.yandex.practicum.filmorate.model.film.Mpa;

import javax.validation.Valid;
import java.util.Collection;

import static ru.yandex.practicum.filmorate.message.MpaLogMessage.*;

@Slf4j
@RestController
@RequestMapping("/mpa")
@AllArgsConstructor
public class MpaController {
    private final MpaService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mpa add(@Valid @RequestBody Mpa mpa) {
        log.info(REQUEST_ADD_MPA.message(), mpa.getId(), mpa.getName());
        return service.add(mpa);
    }

    @PutMapping
    public Mpa update(@Valid @RequestBody Mpa mpa) {
        log.info(REQUEST_UPDATE_MPA.message(), mpa.getId(), mpa.getName());
        return service.update(mpa);
    }

    @GetMapping("/{id}")
    public Mpa get(@PathVariable("id") long id) {
        log.info(REQUEST_GET_MPA.message(), id);
        return service.get(id);
    }

    @GetMapping
    public Collection<Mpa> getList() {
        log.info(REQUEST_GET_MPA_LIST.message());
        return service.getList();
    }
}