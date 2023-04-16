package ru.yandex.practicum.filmorate.model.film;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(builderMethodName = "filmBuilder")
@NoArgsConstructor
@NonNull
public class FilmDb extends Film {
    private int rate;
    private Mpa mpa;
    private final List<Genre> genres = new ArrayList<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Mpa {
        private byte id;
        private String name;
    }

//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class Genre {
//        private byte id;
//        private String name;
//    }
}