package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.user.UserInMemory;

@Component
public class UserStorage extends AbstractStorage<UserInMemory> {
}