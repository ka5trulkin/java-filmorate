package ru.yandex.practicum.filmorate.validation;

import ru.yandex.practicum.filmorate.model.user.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DefaultUserNameValidator implements ConstraintValidator<DefaultUserName, User> {
    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {
        if ((user.getName() == null) || (user.getName().isBlank())) {
            user.setName(user.getLogin());
        }
        return true;
    }
}