package ru.yandex.practicum.filmorate.exception;

public class RequestException extends RuntimeException {
    public RequestException() {
    }

    public RequestException(String message) {
        super(message);
    }
}