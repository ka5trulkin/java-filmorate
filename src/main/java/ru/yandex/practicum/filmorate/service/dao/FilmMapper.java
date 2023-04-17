package ru.yandex.practicum.filmorate.service.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.film.FilmDb;
import ru.yandex.practicum.filmorate.model.film.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FilmMapper implements RowMapper<FilmDb> {
    private FilmDb getFilmDb(ResultSet rs) throws SQLException {
        FilmDb filmDb = FilmDb.filmBuilder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .releaseDate(rs.getDate("release_date").toLocalDate())
                .duration(rs.getInt("duration"))
                .rate(rs.getInt("rate"))
                .build();
        this.fillMpa(filmDb, rs);
        this.fillGenre(filmDb, rs);
        return filmDb;
    }

    private void fillMpa(FilmDb filmDb, ResultSet rs) throws SQLException {
        filmDb.setMpa(
                filmDb.new Mpa(
                        rs.getByte("mpaId"),
                        rs.getString("mpaName")));
    }

    private void fillGenre(FilmDb filmDb, ResultSet rs) throws SQLException {
        if (rs.getShort("genreId") > 0) {
            int counter = 0;
            do {
                counter++;
                filmDb.getGenres().add(
                        new Genre(
                                rs.getShort("genreId"),
                                rs.getString("genreName")));
                if ((rs.isLast()) || (counter == rs.getInt("genreCounter"))) {
                    return;
                }
            } while (rs.next());
        }
    }

    @Override
    public FilmDb mapRow(ResultSet rs, int rowNum) throws SQLException {
        return this.getFilmDb(rs);
    }
}