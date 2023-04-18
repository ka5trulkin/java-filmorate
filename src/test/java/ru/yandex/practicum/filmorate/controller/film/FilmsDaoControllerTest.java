package ru.yandex.practicum.filmorate.controller.film;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.film.FilmDb;
import ru.yandex.practicum.filmorate.model.film.Mpa;
import ru.yandex.practicum.filmorate.model.user.UserDb;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
class FilmsDaoControllerTest extends AbstractFilmControllerTest<FilmDb, UserDb> {
    private static final String indexFilm = "/films";
    private static final String indexUser = "/users";
    private static final FilmDb validFilm = FilmDb.filmBuilder()
            .name("Чебуратино")
            .description("Римейк Филиппа Киркорова")
            .releaseDate(LocalDate.of(2023, 3, 6))
            .duration(90)
            .build();
    private static final UserDb validUser = UserDb.userBuilder()
            .email("email@new.org")
            .login("temp")
            .name("Иннокентий")
            .birthday(LocalDate.of(1986, 11, 10))
            .build();
    private static final FilmDb testFilm = FilmDb.filmBuilder()
            .name("Чебуратино")
            .description("Римейк Филиппа Киркорова")
            .releaseDate(LocalDate.of(2023, 3, 6))
            .duration(90)
            .rate(1)
            .mpa(new Mpa((short) 1, "G"))
            .build();

    public FilmsDaoControllerTest() {
        super(indexFilm, indexUser, validFilm, validUser, testFilm);
    }

    @Override
    void shouldBeUpdated() throws Exception {
        super.shouldBeUpdated();
    }
}