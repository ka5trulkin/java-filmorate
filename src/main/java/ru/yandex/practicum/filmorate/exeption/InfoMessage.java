package ru.yandex.practicum.filmorate.exeption;

public enum InfoMessage {
    OBJECT_ALREADY_EXISTS("Объект уже существует: "),
    OBJECT_NOT_FOUND("Объект не найден: "),
    GET_USER_LIST("Получение списка пользователей"),
    GET_FILM_LIST("Получение списка фильмов"),
    REPOSITORY_CLEAN("Очистка репозитория");

    private final String message;

    InfoMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
