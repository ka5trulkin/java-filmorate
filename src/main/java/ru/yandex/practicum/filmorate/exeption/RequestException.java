package ru.yandex.practicum.filmorate.exeption;

public class RequestException extends RuntimeException {
    public RequestException(String message) {
        super(message);
    }
}
