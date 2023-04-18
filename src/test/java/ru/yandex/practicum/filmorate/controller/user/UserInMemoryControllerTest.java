package ru.yandex.practicum.filmorate.controller.user;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.user.UserInMemory;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
class UserInMemoryControllerTest extends AbstractUserControllerTest<UserInMemory> {
    @Autowired
    private UserStorage storage;
    private static final UserInMemory validUser = UserInMemory.userBuilder()
            .id(1)
            .email("email@new.org")
            .login("temp")
            .name("Иннокентий")
            .birthday(LocalDate.of(1986, 11, 10))
            .build();
    private static final UserInMemory firstFriend = UserInMemory.userBuilder()
            .id(2)
            .email("temp@email.com")
            .login("firstFriend")
            .name("Кеша")
            .birthday(LocalDate.of(1986, 11, 11))
            .build();
    private static final UserInMemory secondFriend = UserInMemory.userBuilder()
            .id(3)
            .email("temptwo@email.com")
            .login("secondFriend")
            .name("Петя")
            .birthday(LocalDate.of(1986, 11, 12))
            .build();
    private static final String indexUser = "/in-memory-users";

    public UserInMemoryControllerTest() {
        super(validUser, firstFriend, secondFriend, indexUser);
    }

    @AfterEach
    void clearRepository() {
        storage.clear();
    }
}