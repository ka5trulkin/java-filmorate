package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
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

    private boolean isValid(User user) {
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return user.getEmail().isBlank()
                || !user.getEmail().contains("@")
                || user.getLogin().isBlank()
                || !user.getLogin().contains(" ")
                || user.getBirthday().isAfter(LocalDate.now());
    }

    @PostMapping
    public User add(@RequestBody User user) {
        if (!userDatabase.containsKey(user.getId())) {
            log.warn("Ошибка добавления. Пользователь с " + user.getId() + " ID уже существует.");
            throw new ValidationException("Пользователь с " + user.getId() + " ID уже существует.");
        }
        if (isValid(user)) {
            log.warn("Переданы некорректные данные о пользователе: " + user);
            throw new ValidationException("Данные о пользователе не соответствует установленным критериям.");
        }
        log.info("Пользователь " + user.getId() + " ID добавлен.");
        return userDatabase.put(user.getId(), user);
    }

    @PutMapping
    public User update(@RequestBody User user) {
        if (isValid(user)) {
            log.warn("Переданы некорректные данные о пользователе: " + user);
            throw new ValidationException("Данные о пользователе не соответствует установленным критериям.");
        }
        log.info("Пользователь " + user.getId() + " ID обновлен.");
        return userDatabase.put(user.getId(), user);
    }

    @GetMapping
    public List<User> getUserList() {
        log.info("Получение списка пользователей.");
        return new ArrayList<>(userDatabase.values());
    }
}