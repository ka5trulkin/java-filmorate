package ru.yandex.practicum.filmorate.storage.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.storage.GenreService;
import ru.yandex.practicum.filmorate.storage.TinyStorage;

import java.util.Collection;

import static ru.yandex.practicum.filmorate.service.dao.film.GenreSql.*;
import static ru.yandex.practicum.filmorate.message.GenreLogMessage.*;

@Slf4j
@Service
public class GenreServiceImplement extends AbstractTinyService<Genre> implements GenreService {
    @Autowired
    protected GenreServiceImplement(@Qualifier("genreStorage") TinyStorage<Genre> storage) {
        super(storage);
    }

    @Override
    public Genre get(long id) {
        log.info(GET_GENRE.message(), id);
        return super.get(GENRE_GET_SQL.getSql(), id);
    }

    @Override
    public Collection<Genre> getList() {
        log.info(GET_GENRE_LIST.message());
        return super.getList(GENRE_GET_LIST_SQL.getSql());
    }
}