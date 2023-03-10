package ru.yandex.practicum.filmorate.exeption;

public enum InfoMessage {
    OBJECT_ALREADY_EXISTS("Объект уже существует: "),
    OBJECT_NOT_FOUND("Объект не найден: "),
    GET_USER_LIST("Получение списка пользователей"),
    GET_FILM_LIST("Получение списка фильмов"),
    REPOSITORY_CLEAN("Очистка репозитория"),
    REQUEST_ADD_MOVIE("Запрос на добавление фильма Name:{}"),
    REQUEST_UPDATE_MOVIE("Запрос на обновление фильма ID:{} Name:{}"),
    REQUEST_ADD_USER("Запрос на добавление пользователя Login:{}"),
    REQUEST_UPDATE_USER("Запрос на обновление пользователя ID:{} Login:{}"),
    FILM_ADDED("Фильм Name:{} добавлен"),
    FILM_UPDATED("Фильм ID:{}, Name:{} обновлен"),
    USER_ADDED("Пользователь Login:{} добавлен"),
    USER_UPDATED("Пользователь ID:{}, Login:{} обновлен");

    private final String message;

    InfoMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}