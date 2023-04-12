package ru.yandex.practicum.filmorate.service.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.film.FilmDb;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmMapper implements RowMapper<FilmDb> {
    private FilmDb fillFilm(ResultSet rs) throws SQLException {
        FilmDb filmDb = FilmDb.filmBuilder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .releaseDate(rs.getDate("release_date").toLocalDate())
                .duration(rs.getInt("duration"))
                .build();
        if (rs.getByte("genreId") > 0) {
            this.fillGenre(filmDb, rs);
        }
        return filmDb;
    }

    private void fillGenre(FilmDb filmDb, ResultSet rs) throws SQLException {
        List<FilmDb.Genre> genres = new ArrayList<>();
        do {
            genres.add(
                    filmDb.new Genre(
                            rs.getByte("genreId"),
                            rs.getString("genreName")
                    )
            );
        } while (rs.next());
        filmDb.setGenres(genres);
    }

    @Override
    public FilmDb mapRow(ResultSet rs, int rowNum) throws SQLException {
        return this.fillFilm(rs);
    }
}