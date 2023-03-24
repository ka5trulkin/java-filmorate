package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.AbstractStorage;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryUserStorage extends AbstractStorage<User> implements UserStorage {
    private void checkName(User user) {
        if ((user.getName() == null) || (user.getName().isBlank())) {
            user.setName(user.getLogin());
        }
    }

    @Override
    public User add(User user) {
        checkName(user);
        return super.add(user);
    }

    @Override
    public User update(User user) {
        checkName(user);
        return super.update(user);
    }

    @Override
    public User get(long id) {
        return super.get(id, User.class);
    }

    @Override
    public List<User> getFriendList(long id) {
        return super.getList().stream()
                .filter(user -> super.get(id, User.class).getFriends().contains(user.getId()))
                .collect(Collectors.toList());
    }
}