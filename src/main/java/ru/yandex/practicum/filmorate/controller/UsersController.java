package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.interfaces.service.UserService;

import javax.validation.Valid;
import java.util.Collection;

import static ru.yandex.practicum.filmorate.message.UserLogMessage.*;

@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UsersController {
    private final UserService service;

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
    public User get(@PathVariable("id") long id) {
        log.info(REQUEST_GET_USER.message(), id);
        return service.get(id);
    }

    @GetMapping
    public Collection<User> getList() {
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
    public Collection<User> getFriendList(@PathVariable long id) {
        log.info(REQUEST_GET_USER_FRIEND_LIST.message(), id);
        return service.getFriendList(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriendList(@PathVariable long id, @PathVariable long otherId) {
        log.info(REQUEST_GET_USER_COMMON_FRIEND_LIST.message(), id, otherId);
        return service.getCommonFriendList(id, otherId);
    }
}