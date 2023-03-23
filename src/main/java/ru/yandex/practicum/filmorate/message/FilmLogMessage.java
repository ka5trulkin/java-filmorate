package ru.yandex.practicum.filmorate.message;

public enum FilmLogMessage {
    FILM_STORAGE_CLEAN("Очистка репозитория с фильмами"),
    REQUEST_GET_FILM_LIST("Запрос на получение списка фильмов"),
    REQUEST_ADD_FILM("Запрос на добавление фильма Name:{}"),
    REQUEST_ADD_FILM_LIKE("Запрос на добавление лайка для фильма ID:{} от пользователя ID:{}"),
    REQUEST_UPDATE_FILM("Запрос на обновление фильма ID:{} Name:{}"),
    FILM_ADDED("Фильм Name:{} добавлен"),
    FILM_LIKE_ADDED("Лайк для фильма ID:{} от пользователя ID:{} добавлен"),
    FILM_UPDATED("Фильм ID:{}, Name:{} обновлен"),
    GET_FILM_LIST("Запрос на получение списка фильмов"),
    GET_FILM("Получение фильма ID:{}");

    private final String message;

    FilmLogMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}