package ru.yandex.practicum.filmorate.model.film;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.util.Collection;
import java.util.HashSet;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(builderMethodName = "filmBuilder")
@NoArgsConstructor
@NonNull
public class FilmDb extends Film {
    private int rate;
    private Mpa mpa;
    private final Collection<Genre> genres = new HashSet<>();
}