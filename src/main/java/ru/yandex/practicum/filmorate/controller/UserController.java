package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

import static ru.yandex.practicum.filmorate.message.UserLogMessage.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    UserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User add(@Valid @RequestBody User user) {
        log.info(REQUEST_ADD_USER.message(), user.getLogin());
        return service.add(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info(REQUEST_UPDATE_USER.message(), user.getId(), user.getLogin());
        return service.update(user);
    }

    @GetMapping("/{id}")
    protected User get(@PathVariable("id") long id) {
        return service.get(id);
    }

    @GetMapping
    public List<User> getList() {
        log.info(GET_USER_LIST.message());
        return service.getList();
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

    @GetMapping("/{id}/friends")
    public List<User> getFriendList(@PathVariable long id) {
        log.info(REQUEST_GET_USER_FRIEND_LIST.message(), id);
        return service.getFriendList(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriendList(@PathVariable long id, @PathVariable long otherId) {
        log.info(REQUEST_GET_USER_COMMON_FRIEND_LIST.message(), id, otherId);
        return service.getCommonFriendList(id, otherId);
    }
}