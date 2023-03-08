package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exeption.NoDataException;
import ru.yandex.practicum.filmorate.exeption.RequestException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FilmRepository {
    private final Map<Integer, Film> data;
    private int idCounter;

    public FilmRepository() {
        this.data = new HashMap<>();
    }

    public Film addNewFilm(Film film) {
        if (data.containsKey(film.getId())) {
            throw new RequestException("Фильм с ID: " + film.getId() + " уже создан.");
        }
        film.setId(++idCounter);
        data.put(film.getId(), film);
        return data.get(film.getId());
    }

    public Film updateFilm(Film film) {
        if (!data.containsKey(film.getId())) {
            throw new NoDataException("Фильм с ID: " + film.getId() + " не найден.");
        }
        data.put(film.getId(), film);
        return data.get(film.getId());
    }

    public List<Film> getFilmList() {
        return new ArrayList<>(data.values());
    }

    public void deleteAll() {
        data.clear();
        idCounter = 0;
    }
}