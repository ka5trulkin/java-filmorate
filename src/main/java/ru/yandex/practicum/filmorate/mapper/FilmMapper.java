package ru.yandex.practicum.filmorate.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.model.film.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FilmMapper implements RowMapper<Film> {
    private Film getFilm(ResultSet rs) throws SQLException {
        Film film = Film.filmBuilder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .releaseDate(rs.getDate("release_date").toLocalDate())
                .duration(rs.getInt("duration"))
                .rate(rs.getInt("rate"))
                .build();
        this.fillMpa(film, rs);
        this.fillGenre(film, rs);
        return film;
    }

    private void fillMpa(Film film, ResultSet rs) throws SQLException {
        film.setMpa(
                new Mpa(
                        rs.getInt("mpaId"),
                        rs.getString("mpaName")));
    }

    private void fillGenre(Film film, ResultSet rs) throws SQLException {
        if (rs.getInt("genreId") > 0) {
            int counter = 0;
            do {
                counter++;
                film.getGenres().add(
                        new Genre(
                                rs.getInt("genreId"),
                                rs.getString("genreName")));
                if ((rs.isLast()) || (counter == rs.getInt("genreCounter"))) {
                    return;
                }
            } while (rs.next());
        }
    }

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        return this.getFilm(rs);
    }
}