//package ru.yandex.practicum.filmorate.service.dao;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//import ru.yandex.practicum.filmorate.model.film.Genre;
//import ru.yandex.practicum.filmorate.service.abstracts.AbstractTinyService;
//import ru.yandex.practicum.filmorate.interfaces.service.GenreService;
//import ru.yandex.practicum.filmorate.interfaces.storage.TinyStorage;
//
//import java.util.Collection;
//
//import static ru.yandex.practicum.filmorate.sql.GenreSql.*;
//import static ru.yandex.practicum.filmorate.message.GenreLogMessage.*;
//
//@Slf4j
//@Service
//public class GenreDaoService extends AbstractTinyService<Genre> implements GenreService {
//    @Autowired
//    protected GenreDaoService(@Qualifier("genreStorage") TinyStorage<Genre> storage) {
//        super(storage);
//    }
//
//    @Override
//    public Genre get(long id) {
//        log.info(GET_GENRE.message(), id);
//        return super.get(GENRE_GET_SQL.getSql(), id);
//    }
//
//    @Override
//    public Collection<Genre> getList() {
//        log.info(GET_GENRE_LIST.message());
//        return super.getList(GENRE_GET_LIST_SQL.getSql());
//    }
//}