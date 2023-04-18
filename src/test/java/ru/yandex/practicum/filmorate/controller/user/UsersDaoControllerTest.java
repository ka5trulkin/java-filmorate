package ru.yandex.practicum.filmorate.controller.user;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.user.UserDb;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
class UsersDaoControllerTest extends AbstractUserControllerTest<UserDb> {
    private static final UserDb validUser = UserDb.userBuilder()
            .id(1)
            .email("email@new.org")
            .login("temp")
            .name("Иннокентий")
            .birthday(LocalDate.of(1986, 11, 10))
            .build();
    private static final UserDb firstFriend = UserDb.userBuilder()
        .id(2)
        .email("temp@email.com")
        .login("firstFriend")
        .name("Кеша")
        .birthday(LocalDate.of(1986, 11, 11))
        .build();
    private static final UserDb secondFriend = UserDb.userBuilder()
            .id(3)
            .email("temptwo@email.com")
            .login("secondFriend")
            .name("Петя")
            .birthday(LocalDate.of(1986, 11, 12))
            .build();
    private static final String indexUser = "/users";

    public UsersDaoControllerTest() {
        super(validUser, firstFriend, secondFriend, indexUser);
    }
}