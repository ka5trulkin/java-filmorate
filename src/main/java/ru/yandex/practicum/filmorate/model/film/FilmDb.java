package ru.yandex.practicum.filmorate.model.film;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(builderMethodName = "filmBuilder")
@NoArgsConstructor
@NonNull
public class FilmDb extends AbstractFilm {
    private int rate;
    private Mpa mpa;
    private List<Genre> genres;

    @Data
    public class Mpa {
        private byte id;
        private String name;
    }

    @Data
    @AllArgsConstructor
    public class Genre {
        private byte id;
        private String name;
    }
}