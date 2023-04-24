package ru.yandex.practicum.filmorate.message;

public enum MpaLogMessage {
    REQUEST_ADD_MPA("Запрос на добавление рейтинга ID:'{}', Name:'{}'"),
    REQUEST_UPDATE_MPA("Запрос на обновление рейтинга ID:'{}', Name:'{}'"),
    REQUEST_GET_MPA("Запрос на получение рейтинга ID:'{}'"),
    REQUEST_GET_MPA_LIST("Запрос на получение списка рейтингов"),
    ADD_MPA("Рейтинг ID:'{}', Name:'{}' добавлен"),
    UPDATE_MPA("Рейтинг ID:'{}', Name:'{}' обновлен"),
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