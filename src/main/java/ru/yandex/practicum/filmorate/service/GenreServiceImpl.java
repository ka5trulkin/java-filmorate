package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.interfaces.service.GenreService;
import ru.yandex.practicum.filmorate.interfaces.storage.GenreStorage;
import ru.yandex.practicum.filmorate.model.film.Genre;

import java.util.Collection;

import static ru.yandex.practicum.filmorate.message.GenreLogMessage.*;

@Slf4j
@Service
public class GenreServiceImpl extends AbstractService<Genre, GenreStorage> implements GenreService {
    @Autowired
    protected GenreServiceImpl(GenreStorage storage) {
        super(storage);
    }

    @Override
    public Genre add(Genre genre) {
        log.info(ADD_GENRE.message(), genre.getId(), genre.getName());
        return super.add(genre);
    }

    @Override
    public Genre update(Genre genre) {
        log.info(UPDATE_GENRE.message(), genre.getId(), genre.getName());
        return super.update(genre);
    }

    @Override
    public Genre get(long id) {
        log.info(GET_GENRE.message(), id);
        return super.get(id);
    }

    @Override
    public Collection<Genre> getList() {
        log.info(GET_GENRE_LIST.message());
        return super.getList();
    }
}