package ru.yandex.practicum.filmorate.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.yandex.practicum.filmorate.model.IdHolder;
import ru.yandex.practicum.filmorate.validation.DefaultUserName;
import ru.yandex.practicum.filmorate.validation.NotContainSpace;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(builderMethodName = "userBuilder")
@DefaultUserName
public class User extends IdHolder {
    private String name;
    @Email(message = "Email не соответствует формату электронной почты")
    private String email;
    @NotBlank(message = "Login не должен быть пустым")
    @NotContainSpace(message = "Login не должен содержать пробелы")
    private String login;
    @PastOrPresent(message = "Birthday должно содержать прошедшую дату или сегодняшнее число")
    private LocalDate birthday;
}