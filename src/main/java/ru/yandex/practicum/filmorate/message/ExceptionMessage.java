package ru.yandex.practicum.filmorate.message;

public enum ExceptionMessage {
    OBJECT_ALREADY_EXIST("Объект ID:'%s' уже существует"),
    OBJECT_BY_ID_NOT_FOUND("Объект ID:'%s' не найден"),
    OBJECT_NOT_FOUND("Объект не найден"),
    FILM_LIKE_ALREADY_EXIST("Фильм ID:'%s' уже оценен пользователем ID:'%s'"),
    FILM_LIKE_NOT_FOUND("Фильм ID:'%s' не был оценен пользователем ID:'%s'"),
    BAD_FILM_LIKE("Ошибка оценки фильма ID:'%s' пользователем ID:'%s'"),
    FRIEND_NOT_FOUND("Ошибка добавления в друзья пользователей ID:'%s' и ID:'%s'");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}