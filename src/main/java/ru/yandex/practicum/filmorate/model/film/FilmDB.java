package ru.yandex.practicum.filmorate.model.film;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(builderMethodName = "filmBuilder")
@NoArgsConstructor
@AllArgsConstructor
@NonNull
public class FilmDB extends Film {
    private String mpa;
}