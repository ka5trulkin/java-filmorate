package ru.yandex.practicum.filmorate.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.IdHolder;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Slf4j
@Repository
public class UserRepository extends AbstractRepository {
    private void checkName(User user) {
        if ((user.getName() == null) || (user.getName().isBlank())) {
            user.setName(user.getLogin());
        }
    }

    @Override
    public IdHolder add(IdHolder object) {
        checkName((User) object);
        log.info("Пользователь ID:{} добавлен", object.getId());
        return super.add(object);
    }

    @Override
    public IdHolder update(IdHolder object) {
        checkName((User) object);
        log.info("Пользователь ID:{} обновлен", object.getId());
        return super.update(object);
    }

    @Override
    public List<IdHolder> getList() {
        log.info("Получение списков пользователей");
        return super.getList();
    }

    @Override
    public void deleteAll() {
        log.info("Очистка репозитория с пользователями");
        super.deleteAll();
    }
}