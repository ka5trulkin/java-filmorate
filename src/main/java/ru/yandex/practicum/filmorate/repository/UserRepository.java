package ru.yandex.practicum.filmorate.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

import static ru.yandex.practicum.filmorate.exeption.InfoMessage.GET_USER_LIST;
import static ru.yandex.practicum.filmorate.exeption.InfoMessage.REPOSITORY_CLEAN;

@Slf4j
@Repository
public class UserRepository extends AbstractRepository<User> {
    private void checkName(User user) {
        if ((user.getName() == null) || (user.getName().isBlank())) {
            user.setName(user.getLogin());
        }
    }

    @Override
    public User add(User user) {
        checkName(user);
        log.info("Пользователь Login:{} добавлен", user.getLogin());
        return super.add(user);
    }

    @Override
    public User update(User user) {
        checkName(user);
        log.info("Пользователь ID:{}, Login:{} обновлен", user.getId(), user.getLogin());
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