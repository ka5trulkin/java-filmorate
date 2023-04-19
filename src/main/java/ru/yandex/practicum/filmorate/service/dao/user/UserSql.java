package ru.yandex.practicum.filmorate.service.dao.user;

public enum UserSql {
    SQL_RECEIVE_LIST("SELECT ID, NAME, EMAIL, LOGIN, BIRTHDAY FROM USER_DB"),
    SQL_RECEIVE_BY_ID(String.join(" ", SQL_RECEIVE_LIST.getSql(), "WHERE ID = ?")),
    EXISTS_FRIENDS("EXISTS (SELECT * FROM FRIENDS f WHERE FRIEND_ONE = %d AND FRIEND_TWO = ID)"),
    SQL_RECEIVE_FRIEND_LIST(
            String.join(" ",
                    SQL_RECEIVE_LIST.getSql(),
                    "WHERE",
                    EXISTS_FRIENDS.getSql())),
    SQL_RECEIVE_COMMON_FRIEND_LIST(
            String.join(" ",
                    SQL_RECEIVE_FRIEND_LIST.getSql(),
                    "AND",
                    EXISTS_FRIENDS.getSql())),
    ADD_SQL("INSERT INTO USER_DB (NAME, EMAIL, LOGIN, BIRTHDAY) VALUES(?, ?, ?, ?)"),
    FRIEND_ADD_SQL("INSERT INTO FRIENDS (FRIEND_ONE, FRIEND_TWO) VALUES(?, ?)"),
    FRIEND_DELETE_SQL("DELETE FROM FRIENDS WHERE FRIEND_ONE = ? AND FRIEND_TWO = ?"),
    UPDATE_SQL("UPDATE USER_DB SET NAME = ?, EMAIL = ?, LOGIN = ?, BIRTHDAY = ? WHERE ID = ? ");

    private final String sql;

    UserSql(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}