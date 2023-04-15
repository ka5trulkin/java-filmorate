package ru.yandex.practicum.filmorate.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.user.UserInMemory;
import ru.yandex.practicum.filmorate.service.interfaces.UserService;

@RestController
@RequestMapping("/in-memory-users")
@Slf4j
public class UsersInMemoryController extends AbstractUserController<UserInMemory> {
    @Autowired
    public UsersInMemoryController(@Qualifier("userInMemoryService") UserService<UserInMemory> service) {
        super(service);
    }
}