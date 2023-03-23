package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FilmControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private InMemoryFilmStorage filmStorage;
    @Autowired
    InMemoryUserStorage userStorage;
    @Autowired
    private MockMvc mockMvc;
    private Film validFilm;
    private Film invalidFilm;
    private User validUser;

    @BeforeEach
    void beforeEach() {
        validFilm = Film.builder()
                .name("Чебуратино")
                .description("Римейк Филиппа Киркорова")
                .releaseDate(LocalDate.of(2023, 3, 6))
                .duration(90)
                .build();
        validUser = User.builder()
                .email("email@new.org")
                .login("temp")
                .name("Иннокентий")
                .birthday(LocalDate.of(1986, 11, 10))
                .build();
    }

    @AfterEach
    void clearRepository() {
        filmStorage.clear();
        userStorage.clear();
    }

    @Test
    void shouldBeReturnPopularList() throws Exception {
        mockMvc.perform(get("/films/popular"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
        int mostPopularFilmId = 2;
        int lessPopularFilmId = 1;
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validFilm)));
        mockMvc.perform(post("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validFilm)));
        mockMvc.perform(put("/films/1/like/1"));
        mockMvc.perform(put("/films/2/like/1"));
        mockMvc.perform(put("/films/2/like/2"));
        mockMvc.perform(get("/films/popular"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(mostPopularFilmId))
                .andExpect(jsonPath("$[1].id").value(lessPopularFilmId));
    }

    @Test
    void shouldBeReturnMostPopularFilm() throws Exception {
        mockMvc.perform(get("/films/popular"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
        int mostPopularFilmId = 2;
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validFilm)));
        mockMvc.perform(post("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validFilm)));
        mockMvc.perform(put("/films/1/like/1"));
        mockMvc.perform(put("/films/2/like/1"));
        mockMvc.perform(put("/films/2/like/2"));
        mockMvc.perform(get("/films/popular?count=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(mostPopularFilmId));
    }

    @Test
    void shouldBeRemoveLikeIsOk() throws Exception {
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validFilm)));
        mockMvc.perform(put("/films/1/like/1"));
        mockMvc.perform(delete("/films/1/like/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldBeRemoveLikeException_LikeNotExist() throws Exception {
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validFilm)));
        mockMvc.perform(delete("/films/1/like/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldBeAddLikeIsOk() throws Exception {
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validFilm)));
        mockMvc.perform(put("/films/1/like/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldBeAddLikeException_LikeIsAlreadyExist() throws Exception {
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validFilm)));
        mockMvc.perform(put("/films/1/like/1"));
        mockMvc.perform(put("/films/1/like/1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldBeAddLikeException_UserNotFound() throws Exception {
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validFilm)))
                .andExpect(status().isCreated());
        mockMvc.perform(put("/films/1/like/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldBeAddLikeException_FilmNotFound() throws Exception {
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(put("/films/1/like/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldBeReturnCorrectUser() throws Exception {
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validFilm)))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/films/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Чебуратино"))
                .andExpect(jsonPath("$.description").value("Римейк Филиппа Киркорова"))
                .andExpect(jsonPath("$.releaseDate").value("2023-03-06"))
                .andExpect(jsonPath("$.duration").value("90"));
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
                .andExpect(jsonPath("$.duration").value("90"));
    }

    @Test
    void shouldBeCreateExceptionWithEmptyRequest() throws Exception {
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeCreateExceptionWithExistentId() throws Exception {
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validFilm)));
        validFilm.setId(1);
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validFilm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeNameException() throws Exception {
        invalidFilm = validFilm.toBuilder()
                .name("")
                .build();
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidFilm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeDescriptionException() throws Exception {
        invalidFilm = validFilm.toBuilder()
                .description("Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль. Здесь они хотят " +
                        "разыскать господина Огюста Куглова, который задолжал им деньги, а именно 20 миллионов. " +
                        "о Куглов, который за время «своего отсутствия», стал кандидатом Коломбани.")
                .build();
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidFilm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeReleaseDateExceptionAfter1895_12_28() throws Exception {
        invalidFilm = validFilm.toBuilder()
                .releaseDate(LocalDate.of(1895, 12 , 27))
                .build();
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidFilm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeReleaseDateExceptionBeforeNow() throws Exception {
        invalidFilm = validFilm.toBuilder()
                .releaseDate(LocalDate.now().plusDays(1))
                .build();
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidFilm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeCreatedWithReleaseDate1895_12_28() throws Exception {
        invalidFilm = validFilm.toBuilder()
                .releaseDate(LocalDate.of(1895, 12 , 28))
                .build();
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidFilm)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldBeCreatedWithReleaseDate2000_10_16() throws Exception {
        invalidFilm = validFilm.toBuilder()
                .releaseDate(LocalDate.of(2000, 10 , 16))
                .build();
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidFilm)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldBeDurationExceptionWith0() throws Exception {
        invalidFilm = validFilm.toBuilder()
                .duration(0)
                .build();
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidFilm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeCreatedWithDuration1() throws Exception {
        invalidFilm = validFilm.toBuilder()
                .duration(1)
                .build();
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidFilm)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldBeCreatedWithDuration300() throws Exception {
        invalidFilm = validFilm.toBuilder()
                .duration(300)
                .build();
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidFilm)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldBeUpdated() throws Exception {
        mockMvc.perform(post("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validFilm)));
        String updName = "NewName";
        String updDescription = "NewDescription";
        LocalDate updReleaseDate = LocalDate.of(2000, 12, 31);
        final int updDuration = 777;
        validFilm = validFilm.toBuilder()
                .id(1)
                .name(updName)
                .description(updDescription)
                .releaseDate(updReleaseDate)
                .duration(updDuration)
                .build();
        mockMvc.perform(put("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validFilm)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value(updName))
                .andExpect(jsonPath("$.description").value(updDescription))
                .andExpect(jsonPath("$.releaseDate").value(updReleaseDate.toString()))
                .andExpect(jsonPath("$.duration").value(updDuration));
    }

    @Test
    void shouldBeUpdateExceptionWithNonexistentId() throws Exception {
        validFilm.setId(9999);
        mockMvc.perform(put("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validFilm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeReturnedList() throws Exception {
        mockMvc.perform(post("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validFilm)));
        validFilm.setId(1);
        mockMvc.perform(get("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validFilm)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}