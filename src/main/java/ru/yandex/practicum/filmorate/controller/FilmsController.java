//package ru.yandex.practicum.filmorate.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//import ru.yandex.practicum.filmorate.controller.film.AbstractFilmsControllerMostDelete;
//import ru.yandex.practicum.filmorate.model.film.FilmDb;
//import ru.yandex.practicum.filmorate.service.interfaces.FilmDao;
//
//import javax.validation.Valid;
//
//import static ru.yandex.practicum.filmorate.message.FilmLogMessage.REQUEST_ADD_FILM;
//
//@RestController
//@RequestMapping("/films")
//public class FilmsController extends AbstractFilmsController<FilmDb, FilmDao<FilmDb>> {
//    @Autowired
//    protected FilmsController(@Qualifier("filmDaoService") FilmDao<FilmDb> service) {
//        super(service);
//    }
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public T add(@Valid @RequestBody T film) {
//        log.info(REQUEST_ADD_FILM.message(), film.getName());
//        return service.add(film);
//    }
//
//}