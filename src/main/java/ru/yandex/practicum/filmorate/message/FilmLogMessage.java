package ru.yandex.practicum.filmorate.message;

public enum FilmLogMessage {
    REQUEST_GET_FILM("Запрос на получение фильма ID:{}"),
    REQUEST_GET_FILM_LIST("Запрос на получение списка фильмов"),
    REQUEST_GET_POPULAR_FILM_LIST("Запрос на получение списка популярных фильмов"),
    REQUEST_ADD_FILM("Запрос на добавление фильма Name:{}"),
    REQUEST_ADD_FILM_LIKE("Запрос на добавление лайка для фильма ID:{} от пользователя ID:{}"),
    REQUEST_UPDATE_FILM("Запрос на обновление фильма ID:{} Name:{}"),
    REQUEST_REMOVE_FILM_LIKE_("Запрос на удаление лайка для фильма ID:{} от пользователя ID:{}"),
    FILM_ADDED("Фильм Name:{} добавлен"),
    FILM_LIKE_ADDED("Лайк для фильма ID:{} от пользователя ID:{} добавлен"),
    FILM_LIKE_REMOVED("Лайк для фильма ID:{} от пользователя ID:{} удален"),
    FILM_UPDATED("Фильм ID:{}, Name:{} обновлен"),
    GET_FILM("Получение фильма ID:{}"),
    GET_FILM_LIST("Получение списка фильмов"),
    GET_POPULAR_FILM_LIST("Получение списка популярных фильмов");

    private final String message;

    FilmLogMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}