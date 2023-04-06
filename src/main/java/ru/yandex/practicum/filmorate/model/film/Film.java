package ru.yandex.practicum.filmorate.model.film;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.yandex.practicum.filmorate.model.IdHolder;
import ru.yandex.practicum.filmorate.validation.AfterFirstFilm;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(builderMethodName = "filmBuilder")
@NoArgsConstructor
@AllArgsConstructor
@NonNull
public class Film extends IdHolder {
    @NotBlank(message = "Name не должно быть пустым")
    private String name;
    @NotBlank(message = "Description не должно быть пустым")
    @Size(max = 200, message = "Description размер должен находиться в диапазоне от 0 до 200")
    private String description;
    @PastOrPresent(message = "ReleaseDate должна содержать прошедшую дату или сегодняшнее число")
    @AfterFirstFilm
    private LocalDate releaseDate;
    @Positive(message = "Duration должна быть больше 0")
    private int duration;
}