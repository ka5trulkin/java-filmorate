package ru.yandex.practicum.filmorate.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = AfterFirstFilmValidator.class)
@Documented
public @interface AfterFirstFilm {
    String message() default "ReleaseDate должна быть не ранее 1985.12.28";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}