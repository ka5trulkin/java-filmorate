package ru.yandex.practicum.filmorate.model.film;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(builderMethodName = "filmBuilder")
@NoArgsConstructor
@NonNull
public class FilmDb extends Film {
    private int rate;
    private Mpa mpa;
    private final Set<Genre> genres = new HashSet<>();
}