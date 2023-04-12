package ru.yandex.practicum.filmorate.model.film;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(builderMethodName = "filmBuilder")
@NoArgsConstructor
@AllArgsConstructor
@NonNull
public class FilmDb extends AbstractFilm {
    @NotBlank
    private String mpa;

    @Data
    private class Mpa {
        private byte id;
        private String name;
    }

    @Data
    private class Genre {
        private byte id;
        private String name;
    }
}