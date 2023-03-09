package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.IdHolder;
import ru.yandex.practicum.filmorate.model.User;

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
        return super.add(object);
    }

    @Override
    public IdHolder update(IdHolder object) {
        checkName((User) object);
        return super.update(object);
    }
}