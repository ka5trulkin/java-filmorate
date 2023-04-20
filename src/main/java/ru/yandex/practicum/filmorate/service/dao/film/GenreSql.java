package ru.yandex.practicum.filmorate.service.dao.film;

public enum GenreSql {
    GENRE_GET_SQL("SELECT ID, NAME FROM GENRE WHERE ID = ?"),
    GENRE_GET_LIST_SQL("SELECT ID, NAME FROM GENRE");

    private final String sql;

    GenreSql(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}