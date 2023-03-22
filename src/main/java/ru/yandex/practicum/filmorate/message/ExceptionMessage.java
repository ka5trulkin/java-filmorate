package ru.yandex.practicum.filmorate.message;

public enum ExceptionMessage {
    USER_ALREADY_EXISTS("Пользователь ID:%s уже существует"),
    FILM_ALREADY_EXISTS("Фильм ID:%s уже существует"),
    OBJECT_NOT_FOUND("Объект не найден: "),
//    GET_USER_LIST("Получение списка пользователей"),
//    GET_FILM_LIST("Получение списка фильмов"),
//    REPOSITORY_CLEAN("Очистка репозитория"),
//    REQUEST_ADD_MOVIE("Запрос на добавление фильма Name:{}"),
//    REQUEST_UPDATE_MOVIE("Запрос на обновление фильма ID:{} Name:{}"),
//    REQUEST_ADD_USER("Запрос на добавление пользователя Login:{}"),
//    REQUEST_UPDATE_USER("Запрос на обновление пользователя ID:{} Login:{}"),
//    FILM_ADDED("Фильм Name:{} добавлен"),
//    FILM_UPDATED("Фильм ID:{}, Name:{} обновлен"),
//    USER_ADDED("Пользователь Login:{} добавлен"),
//    USER_UPDATED("Пользователь ID:{}, Login:{} обновлен"),
//    USER_GET("Получение пользователя ID:{}"),
    USER_NOT_FOUND("Пользователь ID:%s не найден");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
