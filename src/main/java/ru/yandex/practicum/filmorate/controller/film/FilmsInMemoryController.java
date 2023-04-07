package ru.yandex.practicum.filmorate.controller.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dao.interfaces.FilmDao;
import ru.yandex.practicum.filmorate.model.film.FilmInMemory;

@RestController
@RequestMapping("/in-memory-films")
@Slf4j
public class FilmsInMemoryController extends AbstractFilmsController<FilmInMemory> {
    @Autowired
    protected FilmsInMemoryController(@Qualifier("filmService") FilmDao<FilmInMemory> service) {
        super(service);
    }
}