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
import ru.yandex.practicum.filmorate.model.user.UserInMemory;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FilmInMemoryControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private FilmStorage filmStorage;
    @Autowired
    UserStorage userStorage;
    @Autowired
    private MockMvc mockMvc;
    private ru.yandex.practicum.filmorate.model.film.FilmInMemory validFilm;
    private ru.yandex.practicum.filmorate.model.film.FilmInMemory invalidFilm;
    private UserInMemory validUser;

    @BeforeEach
    void beforeEach() {
        validFilm = ru.yandex.practicum.filmorate.model.film.FilmInMemory.filmBuilder()
                .name("Чебуратино")
                .description("Римейк Филиппа Киркорова")
                .releaseDate(LocalDate.of(2023, 3, 6))
                .duration(90)
                .build();
        invalidFilm = validFilm;
        validUser = UserInMemory.userBuilder()
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
        mockMvc.perform(get("/in-memory-films/popular"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
        int mostPopularFilmId = 2;
        int lessPopularFilmId = 1;
        mockMvc.perform(post("/in-memory-users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post("/in-memory-users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post("/in-memory-films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validFilm)));
        mockMvc.perform(post("/in-memory-films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validFilm)));
        mockMvc.perform(put("/in-memory-films/1/like/1"));
        mockMvc.perform(put("/in-memory-films/2/like/1"));
        mockMvc.perform(put("/in-memory-films/2/like/2"));
        mockMvc.perform(get("/in-memory-films/popular"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(mostPopularFilmId))
                .andExpect(jsonPath("$[1].id").value(lessPopularFilmId));
    }

    @Test
    void shouldBeReturnMostPopularFilm() throws Exception {
        mockMvc.perform(get("/in-memory-films/popular"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
        int mostPopularFilmId = 2;
        mockMvc.perform(post("/in-memory-users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post("/in-memory-users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post("/in-memory-films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validFilm)));
        mockMvc.perform(post("/in-memory-films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validFilm)));
        mockMvc.perform(put("/in-memory-films/1/like/1"));
        mockMvc.perform(put("/in-memory-films/2/like/1"));
        mockMvc.perform(put("/in-memory-films/2/like/2"));
        mockMvc.perform(get("/in-memory-films/popular?count=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(mostPopularFilmId));
    }

    @Test
    void shouldBeRemoveLikeIsOk() throws Exception {
        mockMvc.perform(post("/in-memory-users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post("/in-memory-films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validFilm)));
        mockMvc.perform(put("/in-memory-films/1/like/1"));
        mockMvc.perform(delete("/in-memory-films/1/like/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldBeRemoveLikeException_LikeNotExist() throws Exception {
        mockMvc.perform(post("/in-memory-users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post("/in-memory-films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validFilm)));
        mockMvc.perform(delete("/in-memory-films/1/like/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldBeAddLikeIsOk() throws Exception {
        mockMvc.perform(post("/in-memory-users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post("/in-memory-films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validFilm)));
        mockMvc.perform(put("/in-memory-films/1/like/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldBeAddLikeException_LikeIsAlreadyExist() throws Exception {
        mockMvc.perform(post("/in-memory-users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post("/in-memory-films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validFilm)));
        mockMvc.perform(put("/in-memory-films/1/like/1"));
        mockMvc.perform(put("/in-memory-films/1/like/1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldBeAddLikeException_UserNotFound() throws Exception {
        mockMvc.perform(post("/in-memory-films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validFilm)))
                .andExpect(status().isCreated());
        mockMvc.perform(put("/in-memory-films/1/like/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldBeAddLikeException_FilmNotFound() throws Exception {
        mockMvc.perform(post("/in-memory-users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(put("/in-memory-films/1/like/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldBeReturnCorrectUser() throws Exception {
        mockMvc.perform(post("/in-memory-films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validFilm)))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/in-memory-films/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Чебуратино"))
                .andExpect(jsonPath("$.description").value("Римейк Филиппа Киркорова"))
                .andExpect(jsonPath("$.releaseDate").value("2023-03-06"))
                .andExpect(jsonPath("$.duration").value("90"));
    }

    @Test
    void shouldBeCreated() throws Exception {
        mockMvc.perform(post("/in-memory-films")
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
        mockMvc.perform(post("/in-memory-films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeCreateExceptionWithExistentId() throws Exception {
        mockMvc.perform(post("/in-memory-films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validFilm)));
        validFilm.setId(1);
        mockMvc.perform(post("/in-memory-films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validFilm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeNameException() throws Exception {
        invalidFilm.setName("");
        mockMvc.perform(post("/in-memory-films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidFilm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeDescriptionException() throws Exception {
        invalidFilm.setDescription("Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль. Здесь они хотят " +
                "разыскать господина Огюста Куглова, который задолжал им деньги, а именно 20 миллионов. " +
                "о Куглов, который за время «своего отсутствия», стал кандидатом Коломбани.");
        mockMvc.perform(post("/in-memory-films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidFilm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeReleaseDateExceptionAfter1895_12_28() throws Exception {
        invalidFilm.setReleaseDate(LocalDate.of(1895, 12, 27));
        mockMvc.perform(post("/in-memory-films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidFilm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeReleaseDateExceptionBeforeNow() throws Exception {
        invalidFilm.setReleaseDate(LocalDate.now().plusDays(1));
        mockMvc.perform(post("/in-memory-films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidFilm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeCreatedWithReleaseDate1895_12_28() throws Exception {
        invalidFilm.setReleaseDate(LocalDate.of(1895, 12, 28));
        mockMvc.perform(post("/in-memory-films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidFilm)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldBeCreatedWithReleaseDate2000_10_16() throws Exception {
        invalidFilm.setReleaseDate(LocalDate.of(2000, 10, 16));
        mockMvc.perform(post("/in-memory-films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidFilm)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldBeDurationExceptionWith0() throws Exception {
        invalidFilm.setDuration(0);
        mockMvc.perform(post("/in-memory-films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidFilm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeCreatedWithDuration1() throws Exception {
        invalidFilm.setDuration(1);
        mockMvc.perform(post("/in-memory-films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidFilm)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldBeCreatedWithDuration300() throws Exception {
        invalidFilm.setDuration(300);
        mockMvc.perform(post("/in-memory-films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidFilm)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldBeUpdated() throws Exception {
        mockMvc.perform(post("/in-memory-films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validFilm)));
        String updName = "NewName";
        String updDescription = "NewDescription";
        LocalDate updReleaseDate = LocalDate.of(2000, 12, 31);
        final int updDuration = 777;
        ru.yandex.practicum.filmorate.model.film.FilmInMemory updatedFilm = ru.yandex.practicum.filmorate.model.film.FilmInMemory.filmBuilder()
                .id(1)
                .name(updName)
                .description(updDescription)
                .releaseDate(updReleaseDate)
                .duration(updDuration)
                .build();
        mockMvc.perform(put("/in-memory-films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedFilm)))
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
        mockMvc.perform(put("/in-memory-films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validFilm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeReturnedList() throws Exception {
        mockMvc.perform(post("/in-memory-films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validFilm)));
        validFilm.setId(1);
        mockMvc.perform(get("/in-memory-films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validFilm)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}