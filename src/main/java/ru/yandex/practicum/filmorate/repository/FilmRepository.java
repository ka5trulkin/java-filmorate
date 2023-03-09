package ru.yandex.practicum.filmorate.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@Slf4j
@Repository
public class FilmRepository extends AbstractRepository<Film> {
    @Override
    public Film add(Film film) {
        log.info("Фильм ID:{} добавлен", film.getId());
        return super.add(film);
    }

    @Override
    public Film update(Film film) {
        log.info("Фильм ID:{} обновлен", film.getId());
        return super.update(film);
    }

    @Override
    public List<Film> getList() {
        log.info("Получение списков фильмов");
        return super.getList();
    }

    @Override
    public void deleteAll() {
        log.info("Очистка репозитория с фильмами");
        super.deleteAll();
    }
}