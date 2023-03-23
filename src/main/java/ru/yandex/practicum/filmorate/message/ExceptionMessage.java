package ru.yandex.practicum.filmorate.message;

public enum ExceptionMessage {
    USER_ALREADY_EXIST("Пользователь ID:%s уже существует"),
    USER_NOT_FOUND("Пользователь ID:%s не найден"),
    FILM_ALREADY_EXIST("Фильм ID:%s уже существует"),
    FILM_NOT_FOUND("Фильм ID:%s не найден");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
