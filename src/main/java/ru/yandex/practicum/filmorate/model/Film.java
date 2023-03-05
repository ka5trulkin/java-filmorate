package ru.yandex.practicum.filmorate.model;

import java.time.Duration;
import java.time.LocalDate;

@lombok.Data
@lombok.Builder
@lombok.AllArgsConstructor
@lombok.NonNull
public class Film {
    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
}