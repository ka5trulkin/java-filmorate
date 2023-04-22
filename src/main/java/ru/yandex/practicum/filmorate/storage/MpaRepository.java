package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.interfaces.storage.MpaStorage;
import ru.yandex.practicum.filmorate.model.film.Mpa;

import java.util.Collection;

import static ru.yandex.practicum.filmorate.sql.MpaSql.*;

@Slf4j
@Repository
public class MpaRepository extends AbstractStorage<Mpa> implements MpaStorage {
    @Autowired
    public MpaRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Mpa get(Object... args) {
        return super.get(MPA_GET_SQL.getSql(), new BeanPropertyRowMapper<>(Mpa.class), args);
    }

    @Override
    public Collection<Mpa> getList() {
        return super.getList(MPA_GET_LIST_SQL.getSql(), new BeanPropertyRowMapper<>(Mpa.class));
    }
}