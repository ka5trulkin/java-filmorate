package ru.yandex.practicum.filmorate.storage;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.exception.object.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.IdHolder;

import java.util.Collection;

@AllArgsConstructor
public abstract class AbstractStorage<T extends IdHolder> implements Storage<T> {
    protected final JdbcTemplate jdbcTemplate;

//
//    protected PreparedStatementSetter newArgPreparedStatementSetter(@Nullable Object[] args) {
//        return new ArgumentPreparedStatementSetter(args);
//    }
//
//    private Object getObject(Object[] args) {
//        return Arrays.stream(args).findAny()
//    }
//
    @Override
    public void add(String sql, long id, long friendId) {
        jdbcTemplate.update(sql, (id), friendId);
    }

    public T get(String sql, long id, RowMapper<T> mapper) {
        return jdbcTemplate.query((sql), mapper, id)
                .stream()
                .findAny()
                .orElseThrow(() -> new ObjectNotFoundException(id));
    }

    public Collection<T> getList(String sql, RowMapper<T> mapper) {
        return jdbcTemplate.query(sql, mapper);
    }

    public void update(String sql, Object[] args){
        jdbcTemplate.update(sql, args);
    }

    @Override
    public void delete(String sql, long id, long friendId) {
        jdbcTemplate.update(sql, (id), friendId);
    }
}