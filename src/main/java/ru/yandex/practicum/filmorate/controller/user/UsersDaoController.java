package ru.yandex.practicum.filmorate.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.user.UserDb;
import ru.yandex.practicum.filmorate.service.interfaces.UserDao;

@RestController
@RequestMapping("/users")
@Slf4j
public class UsersDaoController extends AbstractUserController<UserDb> {
    @Autowired
    public UsersDaoController(@Qualifier("userDaoService") UserDao<UserDb> service) {
        super(service);
    }
}