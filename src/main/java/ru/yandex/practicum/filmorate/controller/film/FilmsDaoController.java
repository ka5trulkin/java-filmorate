package ru.yandex.practicum.filmorate.controller.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.service.interfaces.FilmDao;
import ru.yandex.practicum.filmorate.model.film.FilmDb;

@RestController
@RequestMapping("/films")
public class FilmsDaoController extends AbstractFilmsController<FilmDb, FilmDao<FilmDb>> {
    @Autowired
    protected FilmsDaoController(@Qualifier("filmDaoService") FilmDao<FilmDb> service) {
        super(service);
    }

    @GetMapping("/test")
    void ololo() {
        service.testMethod();
    }
}