package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.validation.AfterFirstFilm;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Data
@Builder(toBuilder = true)
//@AllArgsConstructor
@NonNull
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank
    private String name;
    @NotBlank
    @Size(max = 200)
    private String description;
    @PastOrPresent(message = "Дата фильма не должна быть в будущем")
    @AfterFirstFilm(message = "Дата фильма должна быть не ранее 28 декабря 1895 года")
    private LocalDate releaseDate;
    @Positive
    private int duration;
}