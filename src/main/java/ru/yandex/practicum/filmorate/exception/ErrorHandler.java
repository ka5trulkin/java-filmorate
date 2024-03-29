package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    private String getValidationExceptionMessage(MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList())
                .toString();
    }

    @ExceptionHandler(RequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError badRequest(RequestException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseError(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError notFound(NotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseError(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError badValidation(MethodArgumentNotValidException exception) {
        String exceptionMessage = getValidationExceptionMessage(exception);
        log.error(exceptionMessage, exception);
        return new ResponseError(exceptionMessage, HttpStatus.BAD_REQUEST);
    }
}