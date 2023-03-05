package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;
import ru.yandex.practicum.filmorate.FilmorateApplication;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    private User validUser;
    private User invalidUser;
    private UserController userController;
    private int idCounter;

/*    @BeforeEach
    void beforeEach() {
        validUser = User.builder()
                .id(++idCounter)
                .email("email@new.org")
                .login("temp")
                .name("Иннокентий")
                .birthday(LocalDate.of(1986, 11, 10))
                .build();
        userController = new UserController();
    }

    @Test
    void add() {
//        // пустой запрос
//        assertThrowsExactly(NullPointerException.class, () -> userController.add(), "Должна быть ошибка добавления.");
        // email без '@'
        invalidUser = validUser.toBuilder()
                .email("emailnew.org")
                .build();
        assertThrowsExactly(ValidationException.class, () -> userController.add(invalidUser), "Должна быть ошибка добавления.");
        // пустой email
        invalidUser.setEmail("");
        assertThrowsExactly(ValidationException.class, () -> userController.add(invalidUser), "Должна быть ошибка добавления.");
        // login пустой
        invalidUser = validUser.toBuilder()
                .login("")
                .build();
        assertThrowsExactly(ValidationException.class, () -> userController.add(invalidUser), "Должна быть ошибка добавления.");
        // login с пробелами
        invalidUser.setLogin("some login");
        assertThrowsExactly(ValidationException.class, () -> userController.add(invalidUser), "Должна быть ошибка добавления.");
        // дата рождения в будущем
        invalidUser = validUser.toBuilder()
                .birthday(LocalDate.now().plusDays(1))
                .build();
        assertThrowsExactly(ValidationException.class, () -> userController.add(invalidUser), "Должна быть ошибка добавления.");
        // добавление валидного пользователя
        int exceptedSize = 1;
        final User exceptedUser = validUser;
        assertDoesNotThrow(() -> userController.add(validUser), "Пользователь не добавлен.");
        assertEquals(exceptedSize, userController.getUserList().size(), "Списки не совпадают.");
        assertEquals(exceptedUser, userController.getUserList().get(0), "Данные пользователя были изменены.");
        // добавление пользователя с уже существующим ID
        assertThrowsExactly(ValidationException.class, () -> userController.add(validUser), "Должна быть ошибка добавления.");
        // добавление другого пользователя
        final User anotherUser = validUser.toBuilder()
                .id(++idCounter)
                .build();
        assertDoesNotThrow(() -> userController.add(anotherUser), "Пользователь не добавлен.");
        exceptedSize++;
        assertEquals(exceptedSize, userController.getUserList().size(), "Списки не совпадают.");
    }

    @Test
    void update() {

    }*/
}