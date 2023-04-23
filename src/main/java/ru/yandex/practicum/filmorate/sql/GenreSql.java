package ru.yandex.practicum.filmorate.sql;

public enum GenreSql {
    GENRE_GET_SQL("SELECT ID, NAME FROM GENRE WHERE ID = ?"),
    GENRE_GET_LIST_SQL("SELECT ID, NAME FROM GENRE"),
    GENRE_PUT_SQL("MERGE INTO GENRE KEY(ID) VALUES(?, ?)");

    private final String sql;

    GenreSql(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}