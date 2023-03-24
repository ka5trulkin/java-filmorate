package ru.yandex.practicum.filmorate.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.yandex.practicum.filmorate.validation.AfterFirstFilm;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(builderMethodName = "filmBuilder")
@AllArgsConstructor
@NonNull
public class Film extends IdHolder {
    @NotBlank
    private String name;
    @NotBlank
    @Size(max = 200)
    private String description;
    @PastOrPresent
    @AfterFirstFilm
    private LocalDate releaseDate;
    @Positive
    private int duration;
    private final Set<Long> likes = new HashSet<>();
}