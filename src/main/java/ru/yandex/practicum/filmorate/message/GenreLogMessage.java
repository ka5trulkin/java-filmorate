package ru.yandex.practicum.filmorate.message;

public enum GenreLogMessage {
    REQUEST_ADD_GENRE("Запрос на добавление жанра ID:'{}', Name:'{}'"),
    REQUEST_UPDATE_GENRE("Запрос на обновление жанра ID:'{}', Name:'{}'"),
    REQUEST_GET_GENRE("Запрос на получение жанра ID:'{}'"),
    REQUEST_GET_GENRE_LIST("Запрос на получение списка жанров"),
    ADD_GENRE("Жанр ID:'{}', Name:'{}' добавлен"),
    UPDATE_GENRE("Жанр ID:'{}', Name:'{}' обновлен"),
    GET_GENRE_LIST("Получение списка жанров"),
    GET_GENRE("Получение жанра ID:'{}'");

    private final String message;

    GenreLogMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}