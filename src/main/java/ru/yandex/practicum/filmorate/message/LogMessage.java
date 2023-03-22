package ru.yandex.practicum.filmorate.message;

public enum LogMessage {
    GET_USER_LIST("Получение списка пользователей"),
    GET_FILM_LIST("Получение списка фильмов"),
    REPOSITORY_CLEAN("Очистка репозитория"),
    REQUEST_ADD_MOVIE("Запрос на добавление фильма Name:{}"),
    REQUEST_UPDATE_MOVIE("Запрос на обновление фильма ID:{} Name:{}"),
    REQUEST_ADD_USER("Запрос на добавление пользователя Login:{}"),
    REQUEST_ADD_FRIEND("Запрос пользователю ID:{} на добавление в друзья от пользователя ID:{}"),
    REQUEST_REMOVE_FRIEND("Запрос пользователю ID:{} на удаление из друзей от пользователя ID:{}"),
    REQUEST_UPDATE_USER("Запрос на обновление пользователя ID:{} Login:{}"),
    FILM_ADDED("Фильм Name:{} добавлен"),
    FILM_UPDATED("Фильм ID:{}, Name:{} обновлен"),
    USER_ADDED("Пользователь Login:{} ID:{} добавлен"),
    USER_UPDATED("Пользователь ID:{}, Login:{} обновлен"),
    USER_GET("Получение пользователя ID:{}"),
    FILM_GET("Получение фильма ID:{}"),
    USER_FRIEND_ADDED("Пользователю ID:{} добавлен в друзья пользователь ID:{}"),
    USER_FRIEND_REMOVED("У пользователя ID:{} удален из друзей пользователь ID:{}");

    private final String message;

    LogMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}