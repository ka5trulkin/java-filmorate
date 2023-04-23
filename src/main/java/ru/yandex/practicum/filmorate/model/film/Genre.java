package ru.yandex.practicum.filmorate.model.film;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    private int id;
    private String name;
}
