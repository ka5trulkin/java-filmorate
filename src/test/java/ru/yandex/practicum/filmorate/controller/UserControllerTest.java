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
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository repository;
    @Autowired
    private MockMvc mockMvc;
    private User validUser;
    private User invalidUser;

    @BeforeEach
    void beforeEach() {
        validUser = User.builder()
                .email("email@new.org")
                .login("temp")
                .name("Иннокентий")
                .birthday(LocalDate.of(1986, 11, 10))
                .build();
    }

    @AfterEach
    void clearRepository() {
        repository.deleteAll();
    }

    @Test
    void shouldBeCreated() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.email").value("email@new.org"))
                .andExpect(jsonPath("$.login").value("temp"))
                .andExpect(jsonPath("$.name").value("Иннокентий"))
                .andExpect(jsonPath("$.birthday").value("1986-11-10"));
    }

    @Test
    void shouldBeCreateExceptionWithEmptyRequest() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeCreateExceptionWithExistentId() throws Exception {
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        validUser.setId(1);
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeLoginException() throws Exception {
        invalidUser = validUser.toBuilder()
                .login("one two")
                .build();
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeEmailException() throws Exception {
        invalidUser = validUser.toBuilder()
                .email("email.org")
                .build();
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeBirthdayException() throws Exception {
        invalidUser = validUser.toBuilder()
                .birthday(LocalDate.of(2986, 11, 10))
                .build();
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeUpdated() throws Exception {
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        String updEmail = "newEmail@new.org";
        String updLogin = "newLogin";
        String updName = "NewName";
        LocalDate updBirthday = LocalDate.of(2000, 12, 31);
        validUser = validUser.toBuilder()
                .id(1)
                .email(updEmail)
                .login(updLogin)
                .name(updName)
                .birthday(updBirthday)
                .build();
        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.email").value(updEmail))
                .andExpect(jsonPath("$.login").value(updLogin))
                .andExpect(jsonPath("$.name").value(updName))
                .andExpect(jsonPath("$.birthday").value(updBirthday.toString()));
    }

    @Test
    void shouldBeUpdateExceptionWithNonexistentId() throws Exception {
        validUser.setId(9999);
        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeReturnedList() throws Exception {
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        validUser.setId(1);
        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void mustBeAssignedNameBasedOnLogin() throws Exception {
        validUser.setName("");
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(validUser.getLogin()));
        validUser.setId(1);
        validUser.setName(null);
        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(validUser.getLogin()));
    }
}