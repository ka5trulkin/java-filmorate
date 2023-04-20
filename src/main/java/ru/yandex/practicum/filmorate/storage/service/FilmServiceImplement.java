package ru.yandex.practicum.filmorate.storage.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.storage.FilmService;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.Collection;

import static ru.yandex.practicum.filmorate.message.FilmLogMessage.*;
import static ru.yandex.practicum.filmorate.service.dao.film.FilmSql.SQL_RECEIVE_BY_ID;

@Slf4j
@Service
public class FilmServiceImplement extends AbstractService<Film> implements FilmService {
    @Autowired
    protected FilmServiceImplement(@Qualifier("filmStorage") Storage<Film> storage) {
        super(storage);
    }

    @Override
    public Film add(Film film) {
        log.info(FILM_ADDED.message(), film.getName());
        return super.add(film);
    }

    @Override
    public Film update(Film film) {
        log.info(FILM_UPDATED.message(), film.getId(), film.getName());
        return super.update(film);
    }

    @Override
    public Film get(long id) {
        log.info(GET_FILM.message(), id);
        return super.get(SQL_RECEIVE_BY_ID.getSql(), id);
    }

    @Override
    public void addLike(long id, long userId) {
//        try {
//            storage
//            this.incrementRate(id);
//        } catch (DuplicateKeyException e) {
//            throw new FilmLikeAlreadyExistException(id, userId);
//        } catch (DataIntegrityViolationException e) {
//            throw new BadFilmLikeException(id, userId);
//        }
    }

    @Override
    public void removeLike(long id, long userId) {

    }

    @Override
    public Collection<Film> getPopularList(long count) {
        return null;
    }

    @Override
    public Collection<Film> getList() {
        return null;
    }
}