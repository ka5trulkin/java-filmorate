package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.RequestException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import static ru.yandex.practicum.filmorate.message.ExceptionMessage.*;

@Service
public class UserService {
    @Autowired
    UserStorage storage;

    public void addFriend(long id, long friendId) {
        User user = storage.get(id);
        User friend = storage.get(friendId);
        if (user == null) {
            throw new RequestException(String.format(USER_NOT_FOUND.message(), id));
        }
        if (friend == null) {
            throw new RequestException(String.format(USER_NOT_FOUND.message(), friendId));
        }
        user.getFriendList().add(friendId);
        friend.getFriendList().add(id);
    }

//    public void someMethod() {
//        Set<Long> set = new HashSet<>();
//        set.add(11111L);
//        storage.add(new User(23, "oleg@mail.ru", "login", "userName", LocalDate.now(), null));
//    }
}
