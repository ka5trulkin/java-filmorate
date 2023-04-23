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
import ru.yandex.practicum.filmorate.model.film.Mpa;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
class MpaControllerTest {
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected MockMvc mockMvc;
    private final String urlPath = "/mpa";
    private Mpa mpa;

    @BeforeEach
    void BeforeEach() {
        mpa = Mpa.builder()
                .id(777)
                .name("New Mpa")
                .build();
    }

    @Test
    void add() throws Exception {
        mockMvc.perform(post(urlPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mpa)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("777"))
                .andExpect(jsonPath("$.name").value("New Mpa"));
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(post(urlPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mpa)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("777"))
                .andExpect(jsonPath("$.name").value("New Mpa"));
        String updName = "UpdMpa";
        final Mpa updMpa = mpa;
        updMpa.setName(updName);
        mockMvc.perform(put(urlPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updMpa)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mpa.getId()))
                .andExpect(jsonPath("$.name").value(updName));
    }

    @Test
    void getMpaList() throws Exception {
        mockMvc.perform(get(urlPath))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("G"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("PG"))
                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].name").value("PG-13"))
                .andExpect(jsonPath("$[3].id").value(4))
                .andExpect(jsonPath("$[3].name").value("R"))
                .andExpect(jsonPath("$[4].id").value(5))
                .andExpect(jsonPath("$[4].name").value("NC-17"));
    }

    @Test
    void getMpaById() throws Exception {
        mockMvc.perform(get(String.format("%s/%d", urlPath, 4)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.name").value("R"));
    }
}