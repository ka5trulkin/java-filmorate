package ru.yandex.practicum.filmorate.message;

public enum UserLogMessage {
    REQUEST_ADD_USER("Запрос на добавление пользователя Login:{}"),
    REQUEST_ADD_FRIEND("Запрос пользователю ID:{} на добавление в друзья от пользователя ID:{}"),
    REQUEST_REMOVE_FRIEND("Запрос пользователю ID:{} на удаление из друзей от пользователя ID:{}"),
    REQUEST_UPDATE_USER("Запрос на обновление пользователя ID:{} Login:{}"),
    REQUEST_GET_USER("Запрос на получение пользователя ID:{}"),
    REQUEST_GET_USER_FRIEND_LIST("Запрос на получение списка друзей пользователя ID:{}"),
    REQUEST_GET_USER_COMMON_FRIEND_LIST("Запрос на получение списка общих друзей у пользователя ID:{} с пользователем ID:{}"),
    USER_ADDED("Пользователь Login:{} добавлен"),
    USER_UPDATED("Пользователь ID:{}, Login:{} обновлен"),
    USER_FRIEND_ADDED("Пользователю ID:{} добавлен в друзья пользователь ID:{}"),
    USER_FRIEND_REMOVED("У пользователя ID:{} удален из друзей пользователь ID:{}"),
    GET_USER("Получение пользователя ID:{}"),
    GET_USER_LIST("Получение списка пользователей"),
    GET_USER_FRIEND_LIST("Получение списка друзей пользователя ID:{}"),
    GET_USER_COMMON_FRIEND_LIST("Получение списка общих друзей у пользователя ID:{} с пользователем ID:{}");

    private final String message;

    UserLogMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}