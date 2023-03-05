package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeption.DataUpdateException;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    UserRepository repository;

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
        if (repository.isContains(user)) {
            log.warn("Ошибка добавления. Пользователь с ID: " + user.getId() + " уже существует.");
            throw new ValidationException("Пользователь с ID: " + user.getId() + " уже существует.");
        }
        if (isValid(user)) {
            validationError(user);
        }
        log.info("Пользователь " + user.getName() + " добавлен.");
        return repository.addNewUser(user);
    }

    @PutMapping
    public User update(@RequestBody User user) {
        if (isValid(user)) {
            validationError(user);
        }
        if (!repository.isContains(user)) {
            log.warn("Ошибка Обновления. Пользователя с ID: " + user.getId() + " не существует.");
            throw new DataUpdateException("Пользователя с ID: " + user.getId() + " не существует.");
        }
        log.info("Пользователь с ID: " + user.getId() + " обновлен.");
        return repository.updateUser(user);
    }

    @GetMapping
    public List<User> getUserList() {
        log.info("Получение списка пользователей.");
        return repository.getUserList();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public String validationException(ValidationException exception) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataUpdateException.class)
    public String updateException(DataUpdateException exception) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(exception.getMessage());
    }
}