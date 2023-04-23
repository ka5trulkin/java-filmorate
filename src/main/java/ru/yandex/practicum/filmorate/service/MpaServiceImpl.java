package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.interfaces.service.MpaService;
import ru.yandex.practicum.filmorate.interfaces.storage.MpaStorage;
import ru.yandex.practicum.filmorate.model.film.Mpa;

import java.util.Collection;

import static ru.yandex.practicum.filmorate.message.MpaLogMessage.*;

@Slf4j
@Service
public class MpaServiceImpl extends AbstractService<Mpa, MpaStorage> implements MpaService {
    @Autowired
    protected MpaServiceImpl(MpaStorage storage) {
        super(storage);
    }

    @Override
    public Mpa add(Mpa mpa) {
        log.info(ADD_MPA.message(), mpa.getId(), mpa.getName());
        return super.add(mpa);
    }

    @Override
    public Mpa update(Mpa mpa) {
        log.info(UPDATE_MPA.message(), mpa.getId(), mpa.getName());
        return super.update(mpa);
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