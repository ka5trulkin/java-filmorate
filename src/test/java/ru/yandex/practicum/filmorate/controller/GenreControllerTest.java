package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.film.Genre;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
class GenreControllerTest {
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected MockMvc mockMvc;
    private final String urlPath = "/genres";
    private Genre genre;

    @BeforeEach
    void BeforeEach() {
        genre = Genre.builder()
                .id(777)
                .name("New Genre")
                .build();
    }

    @Test
    void add() throws Exception {
        mockMvc.perform(post(urlPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(genre)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("777"))
                .andExpect(jsonPath("$.name").value("New Genre"));
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(post(urlPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(genre)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("777"))
                .andExpect(jsonPath("$.name").value("New Genre"));
        String updName = "Update Name";
        final Genre updGenre = genre;
        updGenre.setName(updName);
        mockMvc.perform(put(urlPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updGenre)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(genre.getId()))
                .andExpect(jsonPath("$.name").value(updName));
    }

    @Test
    void getGenreList() throws Exception {
        mockMvc.perform(get(urlPath))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Комедия"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Драма"))
                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].name").value("Мультфильм"))
                .andExpect(jsonPath("$[3].id").value(4))
                .andExpect(jsonPath("$[3].name").value("Триллер"))
                .andExpect(jsonPath("$[4].id").value(5))
                .andExpect(jsonPath("$[4].name").value("Документальный"))
                .andExpect(jsonPath("$[5].id").value(6))
                .andExpect(jsonPath("$[5].name").value("Боевик"));
    }

    @Test
    void getGenreById() throws Exception {
        mockMvc.perform(get(String.format("%s/%d", urlPath, 4)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.name").value("Триллер"));
    }
}