//package ru.yandex.practicum.filmorate.storage;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//import ru.yandex.practicum.filmorate.interfaces.storage.TinyStorage;
//import ru.yandex.practicum.filmorate.model.film.Mpa;
//
//import java.util.Collection;
//
//@Slf4j
//@Repository
//public class MpaStorage extends AbstractStorage<Mpa> implements TinyStorage<Mpa> {
//    @Autowired
//    public MpaStorage(JdbcTemplate jdbcTemplate) {
//        super(jdbcTemplate);
//    }
//
//    @Override
//    public Mpa get(String sql, Object... args) {
//        return super.get(sql, new BeanPropertyRowMapper<>(Mpa.class), args);
//    }
//
//    @Override
//    public Collection<Mpa> getList(String sql) {
//        return super.getList(sql, new BeanPropertyRowMapper<>(Mpa.class));
//    }
//}