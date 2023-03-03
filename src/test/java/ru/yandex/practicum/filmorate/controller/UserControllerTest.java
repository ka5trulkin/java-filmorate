package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    private User validUser;
    private FilmController filmController;

    @BeforeEach
    void beforeEach() {
        validUser = User.builder()
                .id(1)
                .email("email@new.org")
                .login("temp")
                .birthday(LocalDate.of())
    }

    @Test
    void add() {
        System.out.println("1");
    }

    @Test
    void update() {
        System.out.println("2");
    }
}