package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.film.BadFilmLikeException;
import ru.yandex.practicum.filmorate.exception.film.FilmLikeAlreadyExistException;
import ru.yandex.practicum.filmorate.interfaces.service.FilmService;
import ru.yandex.practicum.filmorate.interfaces.storage.FilmStorage;
import ru.yandex.practicum.filmorate.model.film.Film;

import java.util.Collection;

import static ru.yandex.practicum.filmorate.message.FilmLogMessage.*;

@Slf4j
@Service
public class FilmServiceImpl extends AbstractService<Film, FilmStorage> implements FilmService {
    @Autowired
    protected FilmServiceImpl(FilmStorage storage) {
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
        return super.get(id);
    }

    @Override
    public Collection<Film> getList() {
        log.info(GET_FILM_LIST.message());
        return super.getList();
    }

    @Override
    public void addLike(long id, long userId) {
        try {
            storage.addLike(
                    id,
                    userId);
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
            storage.removeLike(
                    id,
                    userId);
            log.info(FILM_LIKE_REMOVED.message(), id, userId);
        } catch (DuplicateKeyException e) {
            throw new FilmLikeAlreadyExistException(id, userId);
        } catch (DataIntegrityViolationException e) {
            throw new BadFilmLikeException(id, userId);
        }
    }

    @Override
    public Collection<Film> getPopularList(int count) {
        log.info(GET_POPULAR_FILM_LIST.message());
        return storage.getPopularList(count);
    }
}