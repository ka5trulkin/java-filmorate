package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.film.FilmLikeAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.film.FilmLikeNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.message.FilmLogMessage.*;

@Slf4j
@Service
public class FilmService extends AbstractService<Film> {
    @Autowired
    private UserStorage userStorage;

    private FilmService(@Autowired FilmStorage storage) {
        super(storage);
    }

    private void checkUserExist(long userId) {
        userStorage.get(userId);
    }

    private Set<Long> getLikeList(long id) {
        return super.get(id).getLikes();
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
    public List<Film> getList() {
        log.info(GET_FILM_LIST.message());
        return super.getList();
    }

    public void addLike(long id, long userId) {
        this.checkUserExist(userId);
        Set<Long> likeList = this.getLikeList(id);
        if (likeList.add(userId)) {
            log.info(FILM_LIKE_ADDED.message(), id, userId);
            return;
        }
        throw new FilmLikeAlreadyExistException(id, userId);
    }

    public void removeLike(long id, long userId) {
        this.checkUserExist(userId);
        Set<Long> likeList = this.getLikeList(id);
        if (likeList.remove(userId)) {
            log.info(FILM_LIKE_REMOVED.message(), id, userId);
            return;
        }
        throw new FilmLikeNotFoundException(id, userId);
    }

    public List<Film> getPopularList(long count) {
        log.info(GET_POPULAR_FILM_LIST.message());
        return super.getList().stream()
                .sorted(Comparator.comparing(film -> film.getLikes().size(), Comparator.reverseOrder()))
                .limit(count)
                .collect(Collectors.toList());
    }
}