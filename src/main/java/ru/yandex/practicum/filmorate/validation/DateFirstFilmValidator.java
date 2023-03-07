package ru.yandex.practicum.filmorate.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;


public class DateFirstFilmValidator implements ConstraintValidator<DateFirstFilm, LocalDate> {
    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext context) {
        final LocalDate dateFirstFilm = LocalDate.of(1895, 12, 28);
        return localDate.isAfter(dateFirstFilm);
    }
}
