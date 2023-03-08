package ru.yandex.practicum.filmorate.exeption;

public enum ExceptionMessage {
    OBJECT_ALREADY_EXISTS("Объект уже существует"),
    OBJECT_NOT_FOUND("Объект не найден");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
