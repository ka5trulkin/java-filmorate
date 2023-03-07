package ru.yandex.practicum.filmorate.model;

import ru.yandex.practicum.filmorate.validation.DateFirstFilm;

import javax.validation.constraints.*;
import javax.validation.executable.ValidateOnExecution;
import java.time.LocalDate;

@lombok.Data
@lombok.Builder(toBuilder = true)
@lombok.AllArgsConstructor
@lombok.NonNull
public class Film {
    private int id;
    @NotBlank
    private String name;
    @Size(max = 200)
    private String description;
    @PastOrPresent
    @DateFirstFilm
    private LocalDate releaseDate;
    @PositiveOrZero
    private int duration;
}