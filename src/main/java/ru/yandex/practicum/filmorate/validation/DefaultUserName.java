package ru.yandex.practicum.filmorate.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = DefaultUserNameValidator.class)
@Documented
public @interface DefaultUserName {
    String message() default "Name не должно быть пустым";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}