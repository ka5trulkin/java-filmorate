package ru.yandex.practicum.filmorate.controller.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.film.FilmDb;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.service.interfaces.FilmDao;

import java.util.List;

@RestController
public class FilmDependenciesDaoController extends AbstractFilmsController<FilmDb, FilmDao<FilmDb>> {
    @Autowired
    protected FilmDependenciesDaoController(@Qualifier("filmDaoService") FilmDao<FilmDb> service) {
        super(service);
    }

    @GetMapping("/genres")
    List<Genre> getGenreList() {
        return service.getGenreList();
    }

    @GetMapping("/genres/{id}")
    Genre getGenreById(@PathVariable("id") short id) {
        return service.getGenreById(id);
    }
}