package ru.yandex.practicum.filmorate.controller.film;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.film.FilmInMemory;
import ru.yandex.practicum.filmorate.model.user.UserInMemory;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
class FilmInMemoryControllerTest extends AbstractFilmControllerTest<FilmInMemory, UserInMemory> {
    @Autowired
    private FilmStorage filmStorage;
    @Autowired
    private UserStorage userStorage;
    private static final String indexFilm = "/in-memory-films";
    private static final String indexUser = "/in-memory-users";
    private static final FilmInMemory validFilm = FilmInMemory.filmBuilder()
            .name("Чебуратино")
            .description("Римейк Филиппа Киркорова")
            .releaseDate(LocalDate.of(2023, 3, 6))
            .duration(90)
            .build();
    private static final UserInMemory validUser = UserInMemory.userBuilder()
            .email("email@new.org")
            .login("temp")
            .name("Иннокентий")
            .birthday(LocalDate.of(1986, 11, 10))
            .build();
    private static final FilmInMemory testFilm = FilmInMemory.filmBuilder()
            .name("Чебуратино")
            .description("Римейк Филиппа Киркорова")
            .releaseDate(LocalDate.of(2023, 3, 6))
            .duration(90)
            .build();

    public FilmInMemoryControllerTest() {
        super(indexFilm, indexUser, validFilm, validUser, testFilm);
    }

    @AfterEach
    void clearRepository() {
        filmStorage.clear();
        userStorage.clear();
    }
}