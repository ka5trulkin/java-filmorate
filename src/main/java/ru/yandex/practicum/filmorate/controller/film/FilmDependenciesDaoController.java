package ru.yandex.practicum.filmorate.controller.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.film.FilmDb;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.model.film.Mpa;
import ru.yandex.practicum.filmorate.service.interfaces.FilmDao;

import java.util.List;

import static ru.yandex.practicum.filmorate.message.FilmLogMessage.*;

@Slf4j
@RestController
public class FilmDependenciesDaoController extends AbstractFilmsController<FilmDb, FilmDao<FilmDb>> {
    @Autowired
    protected FilmDependenciesDaoController(@Qualifier("filmDaoService") FilmDao<FilmDb> service) {
        super(service);
    }

    @GetMapping("/genres")
    List<Genre> getGenreList() {
        log.info(REQUEST_GET_GENRE_LIST.message());
        return service.getGenreList();
    }

    @GetMapping("/genres/{id}")
    Genre getGenreById(@PathVariable("id") short id) {
        log.info(REQUEST_GET_GENRE.message(), id);
        return service.getGenreById(id);
    }

    @GetMapping("/mpa")
    List<Mpa> getMpaList() {
        log.info(REQUEST_GET_MPA_LIST.message());
        return service.getMpaList();
    }

    @GetMapping("/mpa/{id}")
    Mpa getMpaById(@PathVariable("id") short id) {
        log.info(REQUEST_GET_MPA.message(), id);
        return service.getMpaById(id);
    }
}