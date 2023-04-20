package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.film.Mpa;
import ru.yandex.practicum.filmorate.interfaces.service.MpaService;

import java.util.Collection;

import static ru.yandex.practicum.filmorate.message.MpaLogMessage.*;

@Slf4j
@RestController
@RequestMapping("/mpa")
@AllArgsConstructor
public class MpaController {
    private final MpaService service;

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