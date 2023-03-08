package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Repository;

@Repository
public class FilmRepository extends CustomRepository {
//    private final Map<Integer, Film> data;
//    private int idCounter;
//
//    public FilmRepository() {
//        this.data = new HashMap<>();
//    }

//    public Film addNewFilm(Film film) {
//        if (data.containsKey(film.getId())) {
//            throw new RequestException(OBJECT_ALREADY_EXISTS_ID.getMessage() + film.getId());
//        }
//        film.setId(++idCounter);
//        data.put(film.getId(), film);
//        return data.get(film.getId());
//    }

//    public Film updateFilm(Film film) {
//        if (!data.containsKey(film.getId())) {
//            throw new NoDataException(OBJECT_NOT_FOUND_ID.getMessage() + film.getId());
//        }
//        data.put(film.getId(), film);
//        return data.get(film.getId());
//    }

//    public List<Film> getFilmList() {
//        return new ArrayList<>(data.values());
//    }

//    public void deleteAll() {
//        data.clear();
//        idCounter = 0;
//    }
}