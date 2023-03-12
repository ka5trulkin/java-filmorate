package ru.yandex.practicum.filmorate.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotContainSpaceValidator implements ConstraintValidator<NotContainSpace, String> {
    @Override
    public boolean isValid(String field, ConstraintValidatorContext context) {
        return !field.contains(" ");
    }
}