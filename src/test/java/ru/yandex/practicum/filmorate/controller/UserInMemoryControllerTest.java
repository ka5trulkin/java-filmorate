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
import ru.yandex.practicum.filmorate.storage.memory.UserInMemoryStorage;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserInMemoryControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserInMemoryStorage storage;
    @Autowired
    private MockMvc mockMvc;
    private UserInMemory validUser;
    private UserInMemory invalidUser;
    private UserInMemory firstFriend;
    private UserInMemory secondFriend;

    @BeforeEach
    void beforeEach() {
        validUser = UserInMemory.userBuilder()
                .id(1)
                .email("email@new.org")
                .login("temp")
                .name("Иннокентий")
                .birthday(LocalDate.of(1986, 11, 10))
                .build();
        invalidUser = validUser;
        firstFriend = UserInMemory.userBuilder()
                .id(2)
                .email("temp@email.com")
                .login("firstFriend")
                .name("Кеша")
                .birthday(LocalDate.of(1986, 11, 11))
                .build();
        secondFriend = UserInMemory.userBuilder()
                .id(3)
                .email("temptwo@email.com")
                .login("secondFriend")
                .name("Петя")
                .birthday(LocalDate.of(1986, 11, 12))
                .build();
    }

    @AfterEach
    void clearRepository() {
        storage.clear();
    }

    @Test
    void shouldBeReturnCommonFriendList() throws Exception {
        mockMvc.perform(get("/in-memory-users/1/friends/common/2"))
                .andExpect(status().isNotFound());
        mockMvc.perform(post("/in-memory-users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post("/in-memory-users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firstFriend)));
        mockMvc.perform(post("/in-memory-users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(secondFriend)));
        mockMvc.perform(put("/in-memory-users/1/friends/3"));
        mockMvc.perform(put("/in-memory-users/2/friends/3"));
        mockMvc.perform(get("/in-memory-users/1/friends/common/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(secondFriend.getId()))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void shouldBeReturnFriendList() throws Exception {
        mockMvc.perform(get("/in-memory-users/1/friends"))
                .andExpect(status().isNotFound());
        mockMvc.perform(post("/in-memory-users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post("/in-memory-users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firstFriend)));
        mockMvc.perform(put("/in-memory-users/1/friends/2"));
        mockMvc.perform(get("/in-memory-users/1/friends"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(firstFriend.getId()))
                .andExpect(jsonPath("$", hasSize(1)));
        mockMvc.perform(post("/in-memory-users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(secondFriend)));
        mockMvc.perform(put("/in-memory-users/1/friends/3"));
        mockMvc.perform(get("/in-memory-users/1/friends"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[1].id").value(secondFriend.getId()))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void shouldBeRemovedUserFriend() throws Exception {
        mockMvc.perform(post("/in-memory-users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post("/in-memory-users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firstFriend)));
        mockMvc.perform(delete("/in-memory-users/1/friends/2"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldBeAddedUserFriend() throws Exception {
        mockMvc.perform(post("/in-memory-users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post("/in-memory-users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firstFriend)));
        mockMvc.perform(put("/in-memory-users/1/friends/2"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldBeAddNotExistUserFriendException() throws Exception {
        mockMvc.perform(put("/in-memory-users/1/friends/2"))
                .andExpect(status().isNotFound());
        mockMvc.perform(post("/in-memory-users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(put("/in-memory-users/1/friends/2"))
                .andExpect(status().isNotFound());
        mockMvc.perform(post("/in-memory-users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firstFriend)));
        mockMvc.perform(put("/in-memory-users/1/friends/2"))
                .andExpect(status().isOk());
        mockMvc.perform(put("/in-memory-users/1/friends/777"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldBeReturnCorrectUser() throws Exception {
        mockMvc.perform(post("/in-memory-users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(get("/in-memory-users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.email").value("email@new.org"))
                .andExpect(jsonPath("$.login").value("temp"))
                .andExpect(jsonPath("$.name").value("Иннокентий"))
                .andExpect(jsonPath("$.birthday").value("1986-11-10"));
    }

    @Test
    void shouldBeCreated() throws Exception {
        mockMvc.perform(post("/in-memory-users")
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
        mockMvc.perform(post("/in-memory-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeCreateExceptionWithExistentId() throws Exception {
        mockMvc.perform(post("/in-memory-users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        validUser.setId(1);
        mockMvc.perform(post("/in-memory-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeLoginException() throws Exception {
        invalidUser.setLogin("one two");
        mockMvc.perform(post("/in-memory-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeEmailException() throws Exception {
        invalidUser.setEmail("email.org");
        mockMvc.perform(post("/in-memory-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeBirthdayException() throws Exception {
        invalidUser.setBirthday(LocalDate.of(2986, 11, 10));
        mockMvc.perform(post("/in-memory-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeUpdated() throws Exception {
        mockMvc.perform(post("/in-memory-users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        String updEmail = "newEmail@new.org";
        String updLogin = "newLogin";
        String updName = "NewName";
        LocalDate updBirthday = LocalDate.of(2000, 12, 31);
        UserInMemory updatedUser = UserInMemory.userBuilder()
                .id(1)
                .email(updEmail)
                .login(updLogin)
                .name(updName)
                .birthday(updBirthday)
                .build();
        mockMvc.perform(put("/in-memory-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
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
        mockMvc.perform(put("/in-memory-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeReturnedList() throws Exception {
        mockMvc.perform(post("/in-memory-users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        validUser.setId(1);
        mockMvc.perform(get("/in-memory-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void mustBeAssignedNameBasedOnLogin() throws Exception {
        validUser.setName("");
        mockMvc.perform(post("/in-memory-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(validUser.getLogin()));
        validUser.setId(1);
        validUser.setName(null);
        mockMvc.perform(put("/in-memory-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(validUser.getLogin()));
    }
}