package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final Map<Integer, User> userDatabase = new HashMap<>();
    private int idCounter;

    private boolean isValid(User user) {
        return user.getEmail().isBlank()
                || !user.getEmail().contains("@")
                || user.getLogin().isBlank()
                || user.getLogin().contains(" ")
                || user.getBirthday().isAfter(LocalDate.now());
    }

    private void validationError(User user) {
        log.warn("Переданы некорректные данные о пользователе: " + user);
        throw new ValidationException("Данные о пользователе не соответствует установленным критериям.");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User add(@RequestBody User user) {
        System.out.println(user);
        if (userDatabase.containsKey(user.getId())) {
            log.warn("Ошибка добавления. Пользователь с " + user.getId() + " ID уже существует.");
            throw new ValidationException("Пользователь с " + user.getId() + " ID уже существует.");
        }
        if (isValid(user)) {
            validationError(user);
        }
        user.setId(++idCounter);
        userDatabase.put(user.getId(), user);
        log.info("Пользователь " + user.getId() + " ID добавлен.");
        return userDatabase.get(user.getId());
    }

    @PutMapping
    public User update(@RequestBody User user) {
        if (isValid(user)) {
            validationError(user);
        }
        if (!userDatabase.containsKey(user.getId())) {
            log.warn("Ошибка Обновления. Пользователя с " + user.getId() + " ID не существует.");
            throw new ValidationException("Пользователя с " + user.getId() + " ID не существует.");
        }
        log.info("Пользователь " + user.getId() + " ID обновлен.");
        userDatabase.put(user.getId(), user);
        return userDatabase.get(user.getId());
    }

    @GetMapping
    public List<User> getUserList() {
        log.info("Получение списка пользователей.");
        return new ArrayList<>(userDatabase.values());
    }
}