//package ru.yandex.practicum.filmorate.service.interfaces;
//
//import ru.yandex.practicum.filmorate.model.film.Film;
//
//import java.util.List;
//
//public interface FilmService<T extends Film> extends Dao<T> {
//    void addLike(long id, long userId);
//
//    void removeLike(long id, long userId);
//
//    List<T> getPopularList(long count);
//}