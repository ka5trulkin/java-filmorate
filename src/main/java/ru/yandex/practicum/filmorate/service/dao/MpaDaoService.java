//package ru.yandex.practicum.filmorate.service.dao;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//import ru.yandex.practicum.filmorate.model.film.Mpa;
//import ru.yandex.practicum.filmorate.service.abstracts.AbstractTinyService;
//import ru.yandex.practicum.filmorate.interfaces.service.MpaService;
//import ru.yandex.practicum.filmorate.interfaces.storage.TinyStorage;
//
//import java.util.Collection;
//
//import static ru.yandex.practicum.filmorate.message.MpaLogMessage.GET_MPA;
//import static ru.yandex.practicum.filmorate.message.MpaLogMessage.GET_MPA_LIST;
//import static ru.yandex.practicum.filmorate.sql.MpaSql.MPA_GET_LIST_SQL;
//import static ru.yandex.practicum.filmorate.sql.MpaSql.MPA_GET_SQL;
//
//@Slf4j
//@Service
//public class MpaDaoService extends AbstractTinyService<Mpa> implements MpaService {
//    @Autowired
//    protected MpaDaoService(@Qualifier("mpaStorage") TinyStorage<Mpa> storage) {
//        super(storage);
//    }
//
//    @Override
//    public Mpa get(long id) {
//        log.info(GET_MPA.message(), id);
//        return super.get(MPA_GET_SQL.getSql(), id);
//    }
//
//    @Override
//    public Collection<Mpa> getList() {
//        log.info(GET_MPA_LIST.message());
//        return super.getList(MPA_GET_LIST_SQL.getSql());
//    }
//}