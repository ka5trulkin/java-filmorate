package ru.yandex.practicum.filmorate.controller.user;

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
class AbstractUserControllerTest{
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    private final String urlPath = "/users";
    private User validUser;
    private User firstFriend;
    private User secondFriend;
//    private User testUser;


    @BeforeEach
    void beforeEach() {
        validUser = User.userBuilder()
                .id(1)
                .email("email@new.org")
                .login("temp")
                .name("Иннокентий")
                .birthday(LocalDate.of(1986, 11, 10))
                .build();
        firstFriend = User.userBuilder()
                .id(2)
                .email("temp@email.com")
                .login("firstFriend")
                .name("Кеша")
                .birthday(LocalDate.of(1986, 11, 11))
                .build();
        secondFriend = User.userBuilder()
                .id(3)
                .email("temptwo@email.com")
                .login("secondFriend")
                .name("Петя")
                .birthday(LocalDate.of(1986, 11, 12))
                .build();
//        testUser = validUser;
    }

    @Test
    void shouldBeReturnCommonFriendList() throws Exception {
        mockMvc.perform(post(urlPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post(urlPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firstFriend)));
        mockMvc.perform(post(urlPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(secondFriend)));
        mockMvc.perform(put(String.format("%s/1/friends/3", urlPath)));
        mockMvc.perform(put(String.format("%s/2/friends/3", urlPath)));
        mockMvc.perform(get(String.format("%s/1/friends/common/2", urlPath)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(secondFriend.getId()))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void shouldBeReturnFriendList() throws Exception {
        mockMvc.perform(post(urlPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post(urlPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firstFriend)));
        mockMvc.perform(put(String.format("%s/1/friends/2", urlPath)));
        mockMvc.perform(get(String.format("%s/1/friends", urlPath)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(firstFriend.getId()))
                .andExpect(jsonPath("$", hasSize(1)));
        mockMvc.perform(post(urlPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(secondFriend)));
        mockMvc.perform(put(String.format("%s/1/friends/3", urlPath)));
        mockMvc.perform(get(String.format("%s/1/friends", urlPath)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[1].id").value(secondFriend.getId()))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void shouldBeRemovedUserFriend() throws Exception {
        mockMvc.perform(post(urlPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post(urlPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firstFriend)));
        mockMvc.perform(delete(String.format("%s/1/friends/2", urlPath)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldBeAddedUserFriend() throws Exception {
        mockMvc.perform(post(urlPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post(urlPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firstFriend)));
        mockMvc.perform(put(String.format("%s/1/friends/2", urlPath)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldBeAddNotExistUserFriendException() throws Exception {
        mockMvc.perform(put(String.format("%s/1/friends/2", urlPath)))
                .andExpect(status().isNotFound());
        mockMvc.perform(post(urlPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(put(String.format("%s/1/friends/2", urlPath)))
                .andExpect(status().isNotFound());
        mockMvc.perform(post(urlPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firstFriend)));
        mockMvc.perform(put(String.format("%s/1/friends/2", urlPath)))
                .andExpect(status().isOk());
        mockMvc.perform(put(String.format("%s/1/friends/777", urlPath)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldBeReturnCorrectUser() throws Exception {
        mockMvc.perform(post(urlPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(get(String.format("%s/1", urlPath)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.email").value("email@new.org"))
                .andExpect(jsonPath("$.login").value("temp"))
                .andExpect(jsonPath("$.name").value("Иннокентий"))
                .andExpect(jsonPath("$.birthday").value("1986-11-10"));
    }

    @Test
    void shouldBeCreated() throws Exception {
        mockMvc.perform(post(urlPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(firstFriend)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.email").value("temp@email.com"))
                .andExpect(jsonPath("$.login").value("firstFriend"))
                .andExpect(jsonPath("$.name").value("Кеша"))
                .andExpect(jsonPath("$.birthday").value("1986-11-11"));
    }

    @Test
    void shouldBeCreateExceptionWithEmptyRequest() throws Exception {
        mockMvc.perform(post(urlPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeCreateWithExistentId() throws Exception {
        mockMvc.perform(post(urlPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post(urlPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldBeLoginException() throws Exception {
        validUser.setLogin("one two");
        mockMvc.perform(post(urlPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeEmailException() throws Exception {
        validUser.setEmail("email.org");
        mockMvc.perform(post(urlPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeBirthdayException() throws Exception {
        validUser.setBirthday(LocalDate.of(2986, 11, 10));
        mockMvc.perform(post(urlPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeUpdated() throws Exception {
        mockMvc.perform(post(urlPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        String updEmail = "newEmail@new.org";
        String updLogin = "newLogin";
        String updName = "NewName";
        LocalDate updBirthday = LocalDate.of(2000, 12, 31);
        validUser.setId(1);
        validUser.setEmail(updEmail);
        validUser.setLogin(updLogin);
        validUser.setName(updName);
        validUser.setBirthday(updBirthday);
        mockMvc.perform(put(urlPath)
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
        mockMvc.perform(put(urlPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeReturnedList() throws Exception {
        mockMvc.perform(post(urlPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(get(urlPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void mustBeAssignedNameBasedOnLogin() throws Exception {
        validUser.setName("");
        mockMvc.perform(post(urlPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(validUser.getLogin()));
        validUser.setId(1);
        validUser.setName(null);
        mockMvc.perform(put(urlPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(validUser.getLogin()));
    }
}