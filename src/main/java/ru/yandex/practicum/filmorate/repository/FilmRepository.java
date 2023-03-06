package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FilmRepository {
    private final Map<Integer, Film> filmDatabase;
    private int idCounter;

    public FilmRepository() {
        this.filmDatabase = new HashMap<>();
    }

    public boolean isContains(Film film) {
        return filmDatabase.containsKey(film.getId());
    }

    public Film addNewFilm(Film film) {
        film.setId(++idCounter);
        filmDatabase.put(film.getId(), film);
        return filmDatabase.get(film.getId());
    }

    public Film updateFilm(Film film) {
        filmDatabase.put(film.getId(), film);
        return filmDatabase.get(film.getId());
    }

    public List<Film> getFilmList() {
        return new ArrayList<>(filmDatabase.values());
    }

    public void deleteAll() {
        filmDatabase.clear();
        idCounter = 0;
    }
}