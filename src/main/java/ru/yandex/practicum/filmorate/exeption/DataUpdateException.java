package ru.yandex.practicum.filmorate.exeption;

public class DataUpdateException extends RuntimeException {
    public DataUpdateException(String message) {
        super(message);
    }
}
