package ru.yandex.practicum.filmorate.controller.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dao.interfaces.FilmDao;
import ru.yandex.practicum.filmorate.model.film.Film;

@RestController
@RequestMapping("/films")
public class FilmsController extends AbstractFilmsController<Film> {
    @Autowired
    protected FilmsController(@Qualifier("filmDaoImplement") FilmDao<Film> service) {
        super(service);
    }
}