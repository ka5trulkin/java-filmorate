package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.IdHolder;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController extends AbstractController<User> {
    @Autowired
    private UserController(UserRepository repository) {
        super(repository);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IdHolder add(@Valid @RequestBody User user) {
        log.info("Запрос на добавление пользователя. Login: {}", user.getLogin());
        return super.add(user);
    }

    @PutMapping
    public IdHolder update(@Valid @RequestBody User user) {
        log.info("Запрос на обновление пользователя. Login: {}", user.getLogin());
        return super.update(user);
    }

    @GetMapping
    public List<IdHolder> getUserList() {
        log.info("Получение списка пользователей");
        return super.getList();
    }
}