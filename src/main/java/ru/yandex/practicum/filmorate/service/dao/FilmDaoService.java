package ru.yandex.practicum.filmorate.service.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.film.BadFilmLikeException;
import ru.yandex.practicum.filmorate.exception.film.FilmLikeAlreadyExistException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.service.abstracts.AbstractService;
import ru.yandex.practicum.filmorate.interfaces.service.FilmService;
import ru.yandex.practicum.filmorate.interfaces.storage.Storage;

import java.util.Collection;

import static ru.yandex.practicum.filmorate.message.FilmLogMessage.*;
import static ru.yandex.practicum.filmorate.sql.FilmSql.*;

@Slf4j
@Service
public class FilmDaoService extends AbstractService<Film> implements FilmService {
    @Autowired
    protected FilmDaoService(@Qualifier("filmStorage") Storage<Film> storage) {
        super(storage);
    }

    private void incrementRate(long filmId) {
        super.update(INCREMENT_RATE_SQL.getSql(), filmId);
    }

    private void decrementRate(long filmId, long userID) {
        super.update(
                DECREMENT_RATE_SQL.getSql(),
                filmId,
                userID);
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
    public Collection<Film> getList() {
        log.info(GET_FILM_LIST.message());
        return super.getList(SQL_RECEIVE_LIST.getSql());
    }

    @Override
    public void addLike(long id, long userId) {
        try {
            super.add(
                    LIKE_ADD_SQL.getSql(),
                    id,
                    userId);
            this.incrementRate(id);
            log.info(FILM_LIKE_ADDED.message(), id, userId);
        } catch (DuplicateKeyException e) {
            throw new FilmLikeAlreadyExistException(id, userId);
        } catch (DataIntegrityViolationException e) {
            throw new BadFilmLikeException(id, userId);
        }
    }

    @Override
    public void removeLike(long id, long userId) {
        try {
            super.delete(
                    LIKE_DELETE_SQL.getSql(),
                    id,
                    userId);
            this.decrementRate(id, userId);
            log.info(FILM_LIKE_REMOVED.message(), id, userId);
        } catch (DuplicateKeyException e) {
            throw new FilmLikeAlreadyExistException(id, userId);
        } catch (DataIntegrityViolationException e) {
            throw new BadFilmLikeException(id, userId);
        }
    }

    @Override
    public Collection<Film> getPopularList(long count) {
        log.info(GET_POPULAR_FILM_LIST.message());
        return super.getList(
                SQL_RECEIVE_POPULAR_FILMS.getSql(),
                count);
    }
}