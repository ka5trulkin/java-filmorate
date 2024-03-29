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
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Mpa;
import ru.yandex.practicum.filmorate.model.user.User;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
class FilmControllerTest {
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected MockMvc mockMvc;
    private final String urlPathFilm = "/films";
    private final String urlPathUser = "/users";
    private Film validFilm;
    private User validUser;
    private Film testFilm;

    @BeforeEach
    void beforeEach() {
        validFilm = Film.filmBuilder()
                .name("Чебуратино")
                .description("Римейк Филиппа Киркорова")
                .releaseDate(LocalDate.of(2023, 3, 6))
                .duration(90)
                .build();
        validUser = User.userBuilder()
                .email("email@new.org")
                .login("temp")
                .name("Иннокентий")
                .birthday(LocalDate.of(1986, 11, 10))
                .build();
        testFilm = Film.filmBuilder()
                .name("Чебуратино")
                .description("Римейк Филиппа Киркорова")
                .releaseDate(LocalDate.of(2023, 3, 6))
                .duration(90)
                .rate(1)
                .mpa(new Mpa((short) 1, "G"))
                .build();
    }

    @Test
    void shouldBeReturnPopularList() throws Exception {
        mockMvc.perform(get(String.format("%s/popular", urlPathFilm)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
        int mostPopularFilmId = 2;
        int lessPopularFilmId = 1;
        mockMvc.perform(post(urlPathUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post(urlPathUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post(urlPathFilm)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validFilm)));
        mockMvc.perform(post(urlPathFilm)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validFilm)));
        mockMvc.perform(put(String.format("%s/1/like/1", urlPathFilm)));
        mockMvc.perform(put(String.format("%s/2/like/1", urlPathFilm)));
        mockMvc.perform(put(String.format("%s/2/like/2", urlPathFilm)));
        mockMvc.perform(get(String.format("%s/popular", urlPathFilm)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(mostPopularFilmId))
                .andExpect(jsonPath("$[1].id").value(lessPopularFilmId));
    }

    @Test
    void shouldBeReturnMostPopularFilm() throws Exception {
        mockMvc.perform(get(String.format("%s/popular", urlPathFilm)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
        int mostPopularFilmId = 2;
        mockMvc.perform(post(urlPathUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post(urlPathUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post(urlPathFilm)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validFilm)));
        mockMvc.perform(post(urlPathFilm)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validFilm)));
        mockMvc.perform(put(String.format("%s/1/like/1", urlPathFilm)));
        mockMvc.perform(put(String.format("%s/2/like/1", urlPathFilm)));
        mockMvc.perform(put(String.format("%s/2/like/2", urlPathFilm)));
        mockMvc.perform(get(String.format("%s/popular?count=1", urlPathFilm)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(mostPopularFilmId));
    }

    @Test
    void shouldBeRemoveLikeIsOk() throws Exception {
        mockMvc.perform(post(urlPathUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post(urlPathFilm)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validFilm)));
        mockMvc.perform(put(String.format("%s/1/like/1", urlPathFilm)));
        mockMvc.perform(delete(String.format("%s/1/like/1", urlPathFilm)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldBeRemoveLikeException_LikeNotExist() throws Exception {
        mockMvc.perform(post(urlPathUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post(urlPathFilm)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validFilm)));
        mockMvc.perform(delete(String.format("%s/1/like/1", urlPathFilm)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldBeAddLikeIsOk() throws Exception {
        mockMvc.perform(post(urlPathUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post(urlPathFilm)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validFilm)));
        mockMvc.perform(put(String.format("%s/1/like/1", urlPathFilm)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldBeAddLikeException_LikeIsAlreadyExist() throws Exception {
        mockMvc.perform(post(urlPathUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post(urlPathFilm)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validFilm)));
        mockMvc.perform(put(String.format("%s/1/like/1", urlPathFilm)));
        mockMvc.perform(put(String.format("%s/1/like/1", urlPathFilm)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldBeAddLikeException_BadRequest() throws Exception {
        mockMvc.perform(post(urlPathFilm)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validFilm)))
                .andExpect(status().isCreated());
        mockMvc.perform(put(String.format("%s/1/like/1", urlPathFilm)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldBeAddLikeException_FilmBadRequest() throws Exception {
        mockMvc.perform(post(urlPathUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(put(String.format("%s/1/like/1", urlPathFilm)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldBeReturnCorrectUser() throws Exception {
        mockMvc.perform(post(urlPathFilm)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validFilm)))
                .andExpect(status().isCreated());
        mockMvc.perform(get(String.format("%s/1", urlPathFilm)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Чебуратино"))
                .andExpect(jsonPath("$.description").value("Римейк Филиппа Киркорова"))
                .andExpect(jsonPath("$.releaseDate").value("2023-03-06"))
                .andExpect(jsonPath("$.duration").value("90"));
    }

    @Test
    void shouldBeCreated() throws Exception {
        mockMvc.perform(post(urlPathFilm)
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
        mockMvc.perform(post(urlPathFilm)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeNameException() throws Exception {
        testFilm.setName("");
        mockMvc.perform(post(urlPathFilm)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testFilm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeDescriptionException() throws Exception {
        testFilm.setDescription("Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль. Здесь они хотят " +
                "разыскать господина Огюста Куглова, который задолжал им деньги, а именно 20 миллионов. " +
                "о Куглов, который за время «своего отсутствия», стал кандидатом Коломбани.");
        mockMvc.perform(post(urlPathFilm)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testFilm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeReleaseDateExceptionAfter1895_12_28() throws Exception {
        testFilm.setReleaseDate(LocalDate.of(1895, 12, 27));
        mockMvc.perform(post(urlPathFilm)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testFilm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeReleaseDateExceptionBeforeNow() throws Exception {
        testFilm.setReleaseDate(LocalDate.now().plusDays(1));
        mockMvc.perform(post(urlPathFilm)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testFilm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeCreatedWithReleaseDate1895_12_28() throws Exception {
        testFilm.setReleaseDate(LocalDate.of(1895, 12, 28));
        mockMvc.perform(post(urlPathFilm)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testFilm)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldBeCreatedWithReleaseDate2000_10_16() throws Exception {
        testFilm.setReleaseDate(LocalDate.of(2000, 10, 16));
        mockMvc.perform(post(urlPathFilm)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testFilm)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldBeDurationExceptionWith0() throws Exception {
        testFilm.setDuration(0);
        mockMvc.perform(post(urlPathFilm)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testFilm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeCreatedWithDuration1() throws Exception {
        testFilm.setDuration(1);
        mockMvc.perform(post(urlPathFilm)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testFilm)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldBeCreatedWithDuration300() throws Exception {
        testFilm.setDuration(300);
        mockMvc.perform(post(urlPathFilm)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testFilm)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldBeUpdated() throws Exception {
        mockMvc.perform(post(urlPathFilm)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validFilm)));
        String updName = "NewName";
        String updDescription = "NewDescription";
        LocalDate updReleaseDate = LocalDate.of(2000, 12, 31);
        final int updDuration = 777;
        testFilm.setId(1);
        testFilm.setName(updName);
        testFilm.setDescription(updDescription);
        testFilm.setReleaseDate(updReleaseDate);
        testFilm.setDuration(updDuration);
        mockMvc.perform(put(urlPathFilm)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testFilm)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value(updName))
                .andExpect(jsonPath("$.description").value(updDescription))
                .andExpect(jsonPath("$.releaseDate").value(updReleaseDate.toString()))
                .andExpect(jsonPath("$.duration").value(updDuration));
    }

    @Test
    void shouldBeUpdateExceptionWithNonexistentId() throws Exception {
        testFilm.setId(9999);
        mockMvc.perform(put(urlPathFilm)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testFilm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeReturnedList() throws Exception {
        mockMvc.perform(post(urlPathFilm)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testFilm)));
        testFilm.setId(1);
        mockMvc.perform(get(urlPathFilm)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testFilm)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}