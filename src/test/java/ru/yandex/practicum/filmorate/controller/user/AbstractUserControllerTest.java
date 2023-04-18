package ru.yandex.practicum.filmorate.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.user.User;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

abstract class AbstractUserControllerTest<T extends User> {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    private final String indexUser;
    private final T validUser;
    private final T firstFriend;
    private final T secondFriend;
    private T testUser;

    public AbstractUserControllerTest(T validUser, T firstFriend, T secondFriend, String indexUser) {
        this.validUser = validUser;
        this.firstFriend = firstFriend;
        this.secondFriend = secondFriend;
        this.indexUser = indexUser;
    }

    @BeforeEach
    void beforeEach() {
        testUser = validUser;
    }

    @Test
    void shouldBeReturnCommonFriendList() throws Exception {
        mockMvc.perform(post(indexUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post(indexUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firstFriend)));
        mockMvc.perform(post(indexUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(secondFriend)));
        mockMvc.perform(put(String.format("%s/1/friends/3", indexUser)));
        mockMvc.perform(put(String.format("%s/2/friends/3", indexUser)));
        mockMvc.perform(get(String.format("%s/1/friends/common/2", indexUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(secondFriend.getId()))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void shouldBeReturnFriendList() throws Exception {
        mockMvc.perform(post(indexUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post(indexUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firstFriend)));
        mockMvc.perform(put(String.format("%s/1/friends/2", indexUser)));
        mockMvc.perform(get(String.format("%s/1/friends", indexUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(firstFriend.getId()))
                .andExpect(jsonPath("$", hasSize(1)));
        mockMvc.perform(post(indexUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(secondFriend)));
        mockMvc.perform(put(String.format("%s/1/friends/3", indexUser)));
        mockMvc.perform(get(String.format("%s/1/friends", indexUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[1].id").value(secondFriend.getId()))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void shouldBeRemovedUserFriend() throws Exception {
        mockMvc.perform(post(indexUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post(indexUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firstFriend)));
        mockMvc.perform(delete(String.format("%s/1/friends/2", indexUser)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldBeAddedUserFriend() throws Exception {
        mockMvc.perform(post(indexUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post(indexUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firstFriend)));
        mockMvc.perform(put(String.format("%s/1/friends/2", indexUser)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldBeAddNotExistUserFriendException() throws Exception {
        mockMvc.perform(put(String.format("%s/1/friends/2", indexUser)))
                .andExpect(status().isNotFound());
        mockMvc.perform(post(indexUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(put(String.format("%s/1/friends/2", indexUser)))
                .andExpect(status().isNotFound());
        mockMvc.perform(post(indexUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firstFriend)));
        mockMvc.perform(put(String.format("%s/1/friends/2", indexUser)))
                .andExpect(status().isOk());
        mockMvc.perform(put(String.format("%s/1/friends/777", indexUser)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldBeReturnCorrectUser() throws Exception {
        mockMvc.perform(post(indexUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(get(String.format("%s/1", indexUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.email").value("email@new.org"))
                .andExpect(jsonPath("$.login").value("temp"))
                .andExpect(jsonPath("$.name").value("Иннокентий"))
                .andExpect(jsonPath("$.birthday").value("1986-11-10"));
    }

    @Test
    void shouldBeCreated() throws Exception {
        mockMvc.perform(post(indexUser)
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
        mockMvc.perform(post(indexUser)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeCreateExceptionWithExistentId() throws Exception {
        mockMvc.perform(post(indexUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(post(indexUser)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeLoginException() throws Exception {
        testUser.setLogin("one two");
        mockMvc.perform(post(indexUser)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeEmailException() throws Exception {
        testUser.setEmail("email.org");
        mockMvc.perform(post(indexUser)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeBirthdayException() throws Exception {
        testUser.setBirthday(LocalDate.of(2986, 11, 10));
        mockMvc.perform(post(indexUser)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeUpdated() throws Exception {
        mockMvc.perform(post(indexUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        String updEmail = "newEmail@new.org";
        String updLogin = "newLogin";
        String updName = "NewName";
        LocalDate updBirthday = LocalDate.of(2000, 12, 31);
        testUser.setId(1);
        testUser.setEmail(updEmail);
        testUser.setLogin(updLogin);
        testUser.setName(updName);
        testUser.setBirthday(updBirthday);
        mockMvc.perform(put(indexUser)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.email").value(updEmail))
                .andExpect(jsonPath("$.login").value(updLogin))
                .andExpect(jsonPath("$.name").value(updName))
                .andExpect(jsonPath("$.birthday").value(updBirthday.toString()));
    }

    @Test
    void shouldBeUpdateExceptionWithNonexistentId() throws Exception {
        testUser.setId(9999);
        mockMvc.perform(put(indexUser)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeReturnedList() throws Exception {
        mockMvc.perform(post(indexUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));
        mockMvc.perform(get(indexUser)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void mustBeAssignedNameBasedOnLogin() throws Exception {
        testUser.setName("");
        mockMvc.perform(post(indexUser)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(testUser.getLogin()));
        testUser.setId(1);
        testUser.setName(null);
        mockMvc.perform(put(indexUser)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(testUser.getLogin()));
    }
}