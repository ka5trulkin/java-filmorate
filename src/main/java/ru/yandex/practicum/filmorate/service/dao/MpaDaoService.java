package ru.yandex.practicum.filmorate.service.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.interfaces.service.MpaService;
import ru.yandex.practicum.filmorate.interfaces.storage.MpaStorage;
import ru.yandex.practicum.filmorate.model.film.Mpa;
import ru.yandex.practicum.filmorate.service.abstracts.AbstractTinyService;

import java.util.Collection;

import static ru.yandex.practicum.filmorate.message.MpaLogMessage.*;

@Slf4j
@Service
public class MpaDaoService extends AbstractTinyService<Mpa, MpaStorage> implements MpaService {
    @Autowired
    protected MpaDaoService(MpaStorage storage) {
        super(storage);
    }

    @Override
    public Mpa get(long id) {
        log.info(GET_MPA.message(), id);
        return super.get(id);
    }

    @Override
    public Collection<Mpa> getList() {
        log.info(GET_MPA_LIST.message());
        return super.getList();
    }
}