package ru.yandex.practicum.filmorate.service.memory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.Storage;
import ru.yandex.practicum.filmorate.storage.UserService;

import java.util.List;

@Service
public class UserServiceImplement extends AbstractService<User> implements UserService {
    @Autowired
    protected UserServiceImplement(@Qualifier("userStorage") Storage<User> storage) {
        super(storage);
    }

    @Override
    public User get(long id) {
        return null;
    }

    @Override
    public List<User> getList() {
        return null;
    }

    @Override
    public void addFriend(long id, long friendId) {

    }

    @Override
    public void removeFriend(long id, long friendId) {

    }

    @Override
    public List<User> getFriendList(long id) {
        return null;
    }

    @Override
    public List<User> getCommonFriendList(long id, long otherId) {
        return null;
    }
}
