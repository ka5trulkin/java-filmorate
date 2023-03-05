package ru.yandex.practicum.filmorate.model;

import lombok.NonNull;

import java.time.LocalDate;

@lombok.Data
@lombok.Builder
@lombok.AllArgsConstructor
public class User {
    private int id;
    @NonNull
    private String email;
    @NonNull
    private String login;
    private String name;
    @NonNull
    private LocalDate birthday;
}