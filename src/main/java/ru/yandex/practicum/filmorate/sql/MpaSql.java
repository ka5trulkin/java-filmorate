package ru.yandex.practicum.filmorate.sql;

public enum MpaSql {
    MPA_GET_SQL("SELECT ID, NAME FROM MPA WHERE ID = ?"),
    MPA_GET_LIST_SQL("SELECT ID, NAME FROM MPA");

    private final String sql;

    MpaSql(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}