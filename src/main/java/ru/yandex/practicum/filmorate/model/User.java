package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import ru.yandex.practicum.filmorate.validation.NotContainSpace;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@SuperBuilder(builderMethodName = "userBuilder")
public class User extends IdHolder {
    private String name;
    @Email(message = "Email не соответствует формату электронной почты")
    private String email;
    @NotBlank(message = "Login не должен быть пустым")
    @NotContainSpace(message = "Login не должен содержать пробелы")
    private String login;
    @PastOrPresent(message = "Birthday должно содержать прошедшую дату или сегодняшнее число")
    private LocalDate birthday;
    private final Set<Long> friends = new HashSet<>();
}