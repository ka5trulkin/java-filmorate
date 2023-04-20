package ru.yandex.practicum.filmorate.message;

public enum MpaLogMessage {
    REQUEST_GET_MPA("Запрос на получение рейтинга ID:'{}'"),
    REQUEST_GET_MPA_LIST("Запрос на получение списка рейтингов"),
    GET_MPA_LIST("Получение списка рейтингов"),
    GET_MPA("Получение рейтинга ID:'{}'");

    private final String message;

    MpaLogMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}