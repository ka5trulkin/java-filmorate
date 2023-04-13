package ru.yandex.practicum.filmorate.message;

public enum ExceptionMessage {
    OBJECT_ALREADY_EXIST("Объект ID:'%s' уже существует"),
    OBJECT_NOT_FOUND("Объект ID:'%s' не найден"),
    FILM_LIKE_ALREADY_EXIST("Фильм ID:'%s' уже оценен пользователем ID:'%s'"),
    FILM_LIKE_NOT_FOUND("Фильм ID:'%s' не был оценен пользователем ID:'%s'");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}