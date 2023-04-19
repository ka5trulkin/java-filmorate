package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.IdHolder;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.Service;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.Collection;
import java.util.List;

import static ru.yandex.practicum.filmorate.message.UserLogMessage.GET_USER;
import static ru.yandex.practicum.filmorate.message.UserLogMessage.GET_USER_LIST;
import static ru.yandex.practicum.filmorate.service.dao.user.UserSql.SQL_RECEIVE_BY_ID;
import static ru.yandex.practicum.filmorate.service.dao.user.UserSql.SQL_RECEIVE_LIST;

public abstract class AbstractService<T extends IdHolder> implements Service<T> {
    private final Storage<T> storage;

    protected AbstractService(Storage<T> storage) {
        this.storage = storage;
    }

    public T add(T object) {
        return storage.add(object);
    }

    public void add(String sql, long id, long otherId) {
        storage.add(sql, id, otherId);
    }

    public T update(T object) {
        return storage.update(object);
    }

    public T get(String sql, long id) {
        return storage.get(sql, id);
    }

    public Collection<T> getList(String sql) {
        return storage.getList(sql);
    }
}