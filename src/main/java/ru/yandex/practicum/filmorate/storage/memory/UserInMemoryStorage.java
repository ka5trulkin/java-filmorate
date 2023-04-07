package ru.yandex.practicum.filmorate.storage.memory;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.user.UserInMemory;

@Component
public class UserInMemoryStorage extends AbstractInMemoryStorage<UserInMemory> {
}