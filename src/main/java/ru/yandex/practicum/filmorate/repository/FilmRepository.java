package ru.yandex.practicum.filmorate.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.IdHolder;

import java.util.List;

@Slf4j
@Repository
public class FilmRepository extends AbstractRepository {
    @Override
    public IdHolder add(IdHolder object) {
        log.info("Фильм ID:{} добавлен", object.getId());
        return super.add(object);
    }

    @Override
    public IdHolder update(IdHolder object) {
        log.info("Фильм ID:{} обновлен", object.getId());
        return super.update(object);
    }

    @Override
    public List<IdHolder> getList() {
        log.info("Получение списков фильмов");
        return super.getList();
    }

    @Override
    public void deleteAll() {
        log.info("Очистка репозитория с фильмами");
        super.deleteAll();
    }
}