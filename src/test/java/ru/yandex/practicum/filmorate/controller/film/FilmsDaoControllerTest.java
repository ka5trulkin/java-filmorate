//package ru.yandex.practicum.filmorate.controller.film;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.DirtiesContext;
//import ru.yandex.practicum.filmorate.model.film.FilmDb;
//import ru.yandex.practicum.filmorate.model.film.Mpa;
//import ru.yandex.practicum.filmorate.model.user.UserDb;
//
//import java.time.LocalDate;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//@AutoConfigureTestDatabase
//class FilmsDaoControllerTest extends AbstractFilmControllerTest<FilmDb, UserDb> {
//    private static final String indexFilm = "/films";
//    private static final String indexUser = "/users";
//    private final String indexGenre = "/genres";
//    private final String indexMpa = "/mpa";
//    private static final FilmDb validFilm = FilmDb.filmBuilder()
//            .name("Чебуратино")
//            .description("Римейк Филиппа Киркорова")
//            .releaseDate(LocalDate.of(2023, 3, 6))
//            .duration(90)
//            .build();
//    private static final UserDb validUser = UserDb.userBuilder()
//            .email("email@new.org")
//            .login("temp")
//            .name("Иннокентий")
//            .birthday(LocalDate.of(1986, 11, 10))
//            .build();
//    private static final FilmDb testFilm = FilmDb.filmBuilder()
//            .name("Чебуратино")
//            .description("Римейк Филиппа Киркорова")
//            .releaseDate(LocalDate.of(2023, 3, 6))
//            .duration(90)
//            .rate(1)
//            .mpa(new Mpa((short) 1, "G"))
//            .build();
//
//    public FilmsDaoControllerTest() {
//        super(indexFilm, indexUser, validFilm, validUser, testFilm);
//    }
//
//    @Test
//    void getGenreList() throws Exception {
//        mockMvc.perform(get(indexGenre))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$", hasSize(6)))
//                .andExpect(jsonPath("$[0].id").value(1))
//                .andExpect(jsonPath("$[0].name").value("Комедия"))
//                .andExpect(jsonPath("$[1].id").value(2))
//                .andExpect(jsonPath("$[1].name").value("Драма"))
//                .andExpect(jsonPath("$[2].id").value(3))
//                .andExpect(jsonPath("$[2].name").value("Мультфильм"))
//                .andExpect(jsonPath("$[3].id").value(4))
//                .andExpect(jsonPath("$[3].name").value("Триллер"))
//                .andExpect(jsonPath("$[4].id").value(5))
//                .andExpect(jsonPath("$[4].name").value("Документальный"))
//                .andExpect(jsonPath("$[5].id").value(6))
//                .andExpect(jsonPath("$[5].name").value("Боевик"));
//    }
//
//    @Test
//    void getGenreById() throws Exception {
//        mockMvc.perform(get(String.format("%s/%d", indexGenre, 4)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(4))
//                .andExpect(jsonPath("$.name").value("Триллер"));
//    }
//
//    @Test
//    void getMpaList() throws Exception {
//        mockMvc.perform(get(indexMpa))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$", hasSize(5)))
//                .andExpect(jsonPath("$[0].id").value(1))
//                .andExpect(jsonPath("$[0].name").value("G"))
//                .andExpect(jsonPath("$[1].id").value(2))
//                .andExpect(jsonPath("$[1].name").value("PG"))
//                .andExpect(jsonPath("$[2].id").value(3))
//                .andExpect(jsonPath("$[2].name").value("PG-13"))
//                .andExpect(jsonPath("$[3].id").value(4))
//                .andExpect(jsonPath("$[3].name").value("R"))
//                .andExpect(jsonPath("$[4].id").value(5))
//                .andExpect(jsonPath("$[4].name").value("NC-17"));
//    }
//
//    @Test
//    void getMpaById() throws Exception {
//        mockMvc.perform(get(String.format("%s/%d", indexMpa, 4)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(4))
//                .andExpect(jsonPath("$.name").value("R"));
//    }
//}