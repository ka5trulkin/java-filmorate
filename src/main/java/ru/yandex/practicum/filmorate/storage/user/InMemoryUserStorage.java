package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.NoDataException;
import ru.yandex.practicum.filmorate.exeption.RequestException;
import ru.yandex.practicum.filmorate.exeption.user.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exeption.user.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.AbstractStorage;

import java.util.List;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.message.UserLogMessage.USER_STORAGE_CLEAN;

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
        try {
            super.add(user);
        } catch (RequestException e) {
            throw new UserAlreadyExistException(user.getId());
        }
        return user;
    }

    @Override
    public User update(User user) {
        checkName(user);
        try {
            super.update(user);
        } catch (NoDataException e) {
            throw new UserNotFoundException(user.getId());
        }
        return user;
    }

    @Override
    public List<User> getList() {
        return super.getList();
    }

    @Override
    public void clear() {
        log.info(USER_STORAGE_CLEAN.message());
        super.clear();
    }

    @Override
    public User get(long id) {
        User user;
        try {
            user = super.get(id);
        } catch (RuntimeException e) {
            throw new UserNotFoundException(id);
        }
        return user;
    }

    @Override
    public List<User> getFriendList(long id) {
        return super.getList().stream()
                .filter(user -> super.get(id).getFriends().contains(user.getId()))
                .collect(Collectors.toList());
    }
}