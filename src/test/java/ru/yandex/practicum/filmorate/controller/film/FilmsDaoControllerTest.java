package ru.yandex.practicum.filmorate.controller.film;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.film.FilmDb;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)

class FilmsDaoControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    private FilmDb validFilm;

    @BeforeEach
    void beforeEach() {
        validFilm = FilmDb.filmBuilder()
                .name("Чебуратино")
                .description("Римейк Филиппа Киркорова")
                .releaseDate(LocalDate.of(2023, 3, 6))
                .duration(90)
                .mpa("R")
                .build();
    }

    @Test
    void shouldBeCreated() throws Exception {
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validFilm)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Чебуратино"))
                .andExpect(jsonPath("$.description").value("Римейк Филиппа Киркорова"))
                .andExpect(jsonPath("$.releaseDate").value("2023-03-06"))
                .andExpect(jsonPath("$.duration").value("90"))
                .andExpect(jsonPath("$.mpa").value("R"));
    }
}