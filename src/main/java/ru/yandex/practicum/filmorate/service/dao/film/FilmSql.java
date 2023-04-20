package ru.yandex.practicum.filmorate.service.dao.film;

public enum FilmSql {
    SQL_RECEIVE_FILM(
            "SELECT " +
                    "f.ID, " +
                    "f.NAME, " +
                    "f.DESCRIPTION, " +
                    "f.RELEASE_DATE, " +
                    "f.DURATION, " +
                    "f.RATE, " +
                    "m.ID mpaId, " +
                    "m.NAME mpaName, " +
                    "g.ID genreId, " +
                    "g.NAME genreName, " +
                    "(SELECT COUNT(GENRE_ID) FROM FILM_GENRE WHERE f.ID = FILM_ID) genreCounter " +
                    "FROM FILM_DB f " +
                    "LEFT JOIN FILM_GENRE fg ON f.ID = fg.FILM_ID " +
                    "LEFT JOIN GENRE g ON fg.GENRE_ID = g.ID " +
                    "LEFT JOIN FILM_MPA fm ON fm.FILM_ID = f.ID " +
                    "LEFT JOIN MPA m ON fm.MPA_ID = m.ID "),
    SQL_RECEIVE_LIST(
            String.join(" ",
                    SQL_RECEIVE_FILM.getSql(),
                    "ORDER BY f.ID, genreId")),
    SQL_RECEIVE_BY_ID(
            String.join(" ",
                    SQL_RECEIVE_FILM.getSql(),
                    "WHERE f.ID = ?")),
    SQL_RECEIVE_POPULAR_FILMS(
            String.join(" ",
                    SQL_RECEIVE_FILM.getSql(),
                    "ORDER BY RATE DESC LIMIT ?")),
    ADD_SQL("INSERT INTO FILM_DB (NAME, DESCRIPTION, RELEASE_DATE, DURATION, RATE) VALUES(?, ?, ?, ?, ?)"),
//    RATE_ADD_SQL("INSERT INTO RATE (COUNTER, FILM_ID) VALUES(?, ?)"),
//    RATE_UPDATE_SQL("UPDATE RATE SET COUNTER = ? WHERE FILM_ID = ?"),
    INCREMENT_RATE_SQL("UPDATE FILM_DB SET RATE = RATE + 1 WHERE FILM_ID = ?"),
    DECREMENT_RATE_SQL(
            "MERGE INTO FILM_DB F " +
                    "USING (SELECT FILM_ID, USER_ID FROM LIKES) L " +
                    "ON (L.FILM_ID = ? AND L.USER_ID = ? AND L.FILM_ID = F.FILM_ID) " +
                    "WHEN MATCHED AND F.RATE > 0 " +
                    "THEN UPDATE SET F.RATE = F.RATE - 1"),
    MPA_ADD_SQL("INSERT INTO FILM_MPA (MPA_ID, FILM_ID) VALUES(?, ?)"),
    GENRE_ADD_SQL("INSERT INTO FILM_GENRE (FILM_ID, GENRE_ID) VALUES(?, ?)"),
    UPDATE_SQL("UPDATE FILM_DB SET NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ?, RATE = ? WHERE ID = ? "),
    MPA_UPDATE_SQL("UPDATE FILM_MPA SET MPA_ID = ? WHERE FILM_ID = ?"),
    GENRE_DELETE_SQL("DELETE FROM FILM_GENRE WHERE FILM_ID = ?"),
    LIKE_ADD_SQL("INSERT INTO LIKES (FILM_ID, USER_ID) VALUES(?, ?)"),
    LIKE_DELETE_SQL("DELETE FROM LIKES WHERE FILM_ID = ? AND USER_ID = ?"),
    CHECK_IS_LIKED("SELECT LIKED FROM LIKES WHERE FILM_ID = ? AND USER_ID = ?");

    private final String sql;

    FilmSql(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}