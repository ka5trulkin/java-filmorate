package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

import static ru.yandex.practicum.filmorate.exeption.InfoMessage.*;

@Slf4j
@Repository
public class UserRepository extends AbstractRepository<User> implements UserStorage {
    private void checkName(User user) {
        if ((user.getName() == null) || (user.getName().isBlank())) {
            user.setName(user.getLogin());
        }
    }

    @Override
    public User add(User user) {
        checkName(user);
        log.info(USER_ADDED.message(), user.getLogin());
        return super.add(user);
    }

    @Override
    public User update(User user) {
        checkName(user);
        log.info(USER_UPDATED.message(), user.getId(), user.getLogin());
        return super.update(user);
    }

    @Override
    public List<User> getList() {
        log.info(GET_USER_LIST.message());
        return super.getList();
    }

    @Override
    public void deleteAll() {
        log.info(REPOSITORY_CLEAN.message());
        super.deleteAll();
    }
}