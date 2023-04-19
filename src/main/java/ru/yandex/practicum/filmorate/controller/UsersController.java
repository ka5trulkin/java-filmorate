package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.UserService;

import javax.validation.Valid;

import static ru.yandex.practicum.filmorate.message.UserLogMessage.REQUEST_ADD_USER;

@Slf4j
@RestController
@RequestMapping("/users")
public class UsersController extends AbstractController<User, UserService> {
    @Autowired
    protected UsersController(@Qualifier("userServiceImplement") UserService service) {
        super(service);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User add(@Valid @RequestBody User user) {
        log.info(REQUEST_ADD_USER.message(), user.getLogin());
        return service.add(user);
    }
}