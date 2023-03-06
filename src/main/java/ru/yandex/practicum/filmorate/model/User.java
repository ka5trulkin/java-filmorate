package ru.yandex.practicum.filmorate.model;

import lombok.NonNull;

import javax.validation.constraints.Email;
import java.time.LocalDate;

@lombok.Data
@lombok.Builder(toBuilder = true)
public class User {
    private int id;
    @Email
    private String email;
    @NonNull
    private String login;
    private String name;
    @NonNull
    private LocalDate birthday;

    public User(int id, @NonNull String email, @NonNull String login, String name, @NonNull LocalDate birthday) {
        if ((name == null) || (name.isBlank())) {
            name = login;
        }
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }
}