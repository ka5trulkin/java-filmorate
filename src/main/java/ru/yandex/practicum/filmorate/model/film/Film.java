package ru.yandex.practicum.filmorate.model.film;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(builderMethodName = "filmBuilder")
@NoArgsConstructor
@AllArgsConstructor
@NonNull
public class Film extends AbstractFilm {
    @NonNull
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