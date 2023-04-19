//package ru.yandex.practicum.filmorate.service.memory;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//import ru.yandex.practicum.filmorate.exception.RequestException;
//import ru.yandex.practicum.filmorate.exception.film.BadFilmLikeException;
//import ru.yandex.practicum.filmorate.exception.object.ObjectNotFoundException;
//import ru.yandex.practicum.filmorate.model.film.FilmInMemory;
//import ru.yandex.practicum.filmorate.service.interfaces.Dao;
//import ru.yandex.practicum.filmorate.exception.film.FilmLikeAlreadyExistException;
//import ru.yandex.practicum.filmorate.exception.film.FilmLikeNotFoundException;
//import ru.yandex.practicum.filmorate.model.user.UserInMemory;
//import ru.yandex.practicum.filmorate.service.interfaces.FilmService;
//
//import java.util.Comparator;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//import static ru.yandex.practicum.filmorate.message.FilmLogMessage.*;
//
//@Slf4j
//@Service
//public class FilmInMemoryService extends AbstractService<FilmInMemory> implements FilmService<FilmInMemory> {
//    private final Dao<UserInMemory> userStorage;
//
//    @Autowired
//    protected FilmInMemoryService(@Qualifier("filmStorage") Dao<FilmInMemory> storage,
//                                  @Qualifier("userStorage") Dao<UserInMemory> userStorage) {
//        super(storage);
//        this.userStorage = userStorage;
//    }
//
//    private void checkUserExist(long filmId, long userId) {
//        try {
//            userStorage.get(userId);
//        } catch (ObjectNotFoundException e) {
//            throw new BadFilmLikeException(filmId, userId);
//        }
//    }
//
//    private Set<Long> getLikeList(long id) {
//        try {
//            return super.get(id).getLikes();
//        } catch (ObjectNotFoundException e) {
//            throw new RequestException(e.getMessage());
//        }
//    }
//
//    @Override
//    public FilmInMemory add(FilmInMemory film) {
//        log.info(FILM_ADDED.message(), film.getName());
//        return super.add(film);
//    }
//
//    @Override
//    public FilmInMemory update(FilmInMemory film) {
//        log.info(FILM_UPDATED.message(), film.getId(), film.getName());
//        return super.update(film);
//    }
//
//    @Override
//    public FilmInMemory get(long id) {
//        log.info(GET_FILM.message(), id);
//        return super.get(id);
//    }
//
//    @Override
//    public List<FilmInMemory> getList() {
//        log.info(GET_FILM_LIST.message());
//        return super.getList();
//    }
//
//    @Override
//    public void addLike(long id, long userId) {
//        this.checkUserExist(id, userId);
//        Set<Long> likeList = this.getLikeList(id);
//        if (likeList.add(userId)) {
//            log.info(FILM_LIKE_ADDED.message(), id, userId);
//            return;
//        }
//        throw new FilmLikeAlreadyExistException(id, userId);
//    }
//
//    @Override
//    public void removeLike(long id, long userId) {
//        this.checkUserExist(id, userId);
//        Set<Long> likeList = this.getLikeList(id);
//        if (likeList.remove(userId)) {
//            log.info(FILM_LIKE_REMOVED.message(), id, userId);
//            return;
//        }
//        throw new FilmLikeNotFoundException(id, userId);
//    }
//
//    @Override
//    public List<FilmInMemory> getPopularList(long count) {
//        log.info(GET_POPULAR_FILM_LIST.message());
//        return super.getList().stream()
//                .sorted(Comparator.comparing(film -> film.getLikes().size(), Comparator.reverseOrder()))
//                .limit(count)
//                .collect(Collectors.toList());
//    }
//}