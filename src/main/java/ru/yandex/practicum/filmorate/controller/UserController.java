package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import javax.validation.Valid;
import java.util.List;

import static ru.yandex.practicum.filmorate.message.LogMessage.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController extends AbstractController<User> {
    @Autowired
    private UserController(InMemoryUserStorage repository) {
        super(repository);
    }

    @Autowired
    UserService service;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User add(@Valid @RequestBody User user) {
        log.info(REQUEST_ADD_USER.message(), user.getLogin());
        return super.add(user);
    }

    @Override
    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info(REQUEST_UPDATE_USER.message(), user.getId(), user.getLogin());
        return super.update(user);
    }

    @Override
    @GetMapping
    public List<User> getList() {
        log.info(GET_USER_LIST.message());
        return super.getList();
    }

    @Override
    @GetMapping("/{id}")
    protected User get(@PathVariable("id") long id) {
        return super.get(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable long id, @PathVariable long friendId) {
        log.info(REQUEST_ADD_FRIEND.message(), id, friendId);
        service.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable long id, @PathVariable long friendId) {
        log.info(REQUEST_REMOVE_FRIEND.message(), id, friendId);
        service.removeFriend(id, friendId);
    }
}