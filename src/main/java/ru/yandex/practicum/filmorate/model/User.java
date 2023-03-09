package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.NotContainSpace;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class User implements IdHolder{
    private int id;
    @Email
    private String email;
    @NotBlank
    @NotContainSpace
    private String login;
    private String name;
    @PastOrPresent
    private LocalDate birthday;
}