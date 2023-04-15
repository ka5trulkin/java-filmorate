package ru.yandex.practicum.filmorate.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.service.interfaces.UserService;

import javax.validation.Valid;
import java.util.List;

import static ru.yandex.practicum.filmorate.message.UserLogMessage.*;

@Slf4j
public class AbstractUserController<T extends User> {
    private final UserService<T> service;

    public AbstractUserController(UserService<T> service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public T add(@Valid @RequestBody T user) {
        log.info(REQUEST_ADD_USER.message(), user.getLogin());
        return service.add(user);
    }

    @PutMapping
    public T update(@Valid @RequestBody T user) {
        log.info(REQUEST_UPDATE_USER.message(), user.getId(), user.getLogin());
        return service.update(user);
    }

    @GetMapping("/{id}")
    public T get(@PathVariable("id") long id) {
        log.info(REQUEST_GET_USER.message(), id);
        return service.get(id);
    }

    @GetMapping
    public List<T> getList() {
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
    public List<T> getFriendList(@PathVariable long id) {
        log.info(REQUEST_GET_USER_FRIEND_LIST.message(), id);
        return service.getFriendList(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<T> getCommonFriendList(@PathVariable long id, @PathVariable long otherId) {
        log.info(REQUEST_GET_USER_COMMON_FRIEND_LIST.message(), id, otherId);
        return service.getCommonFriendList(id, otherId);
    }
}