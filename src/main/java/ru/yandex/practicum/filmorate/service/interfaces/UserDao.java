package ru.yandex.practicum.filmorate.service.interfaces;

import ru.yandex.practicum.filmorate.model.user.User;

public interface UserDao<T extends User> extends UserService<T> {
    void testMethod();
}