package ru.yandex.practicum.filmorate.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

import static ru.yandex.practicum.filmorate.exeption.InfoMessage.*;

@Slf4j
@Repository
public class FilmRepository extends AbstractRepository<Film> {
    @Override
    public Film add(Film film) {
        log.info("Фильм Name:{} добавлен", film.getName());
        return super.add(film);
    }

    @Override
    public Film update(Film film) {
        log.info("Фильм ID:{}, Name:{} обновлен", film.getId(), film.getName());
        return super.update(film);
    }

    @Override
    public List<Film> getList() {
        log.info(GET_FILM_LIST.message());
        return super.getList();
    }

    @Override
    public void deleteAll() {
        log.info(REPOSITORY_CLEAN.message());
        super.deleteAll();
    }
}