package ru.yandex.practicum.filmorate.service.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.user.UserDb;
import ru.yandex.practicum.filmorate.service.interfaces.UserDao;

import java.util.List;

@Slf4j
@Service
public class UserDaoService extends AbstractDao<UserDb> implements UserDao<UserDb> {
    private final String tableName = "USER_DB";
    private final String sqlReceiveUserList = "SELECT " +
            "ID, NAME, EMAIL, LOGIN, BIRTHDAY " +
            "FROM USER_DB ";
    private final String sqlReceiveFilmById = String.join(" ", sqlReceiveUserList, "WHERE ID = ?");
    private final String userAddSql = "INSERT INTO USER_DB (NAME, EMAIL, LOGIN, BIRTHDAY) VALUES(?, ?, ?, ?)";
    private final String rateAddSql = "INSERT INTO RATE (RATE, FILM_ID) VALUES(?, ?)";
    private final String mpaAddSql = "INSERT INTO FILM_MPA (MPA_ID, FILM_ID) VALUES(?, ?)";
    private final String genreAddSql = "INSERT INTO FILM_GENRE (GENRE_ID, FILM_ID) VALUES(?, ?)";
    private final String filmUpdateSql = "UPDATE FILM_DB " +
            "SET NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ? " +
            "WHERE id = ? ";
    private final String rateUpdateSql = "UPDATE RATE SET RATE = ? WHERE FILM_ID = ?";
    private final String mpaUpdateSql = "UPDATE FILM_MPA SET MPA_ID = ? WHERE FILM_ID = ?";
    private final String genreDeleteSql = "DELETE FROM FILM_GENRE WHERE FILM_ID = ?";

    @Autowired
    protected UserDaoService(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    private void addUserToDb(UserDb user) {
        jdbcTemplate.update(userAddSql,
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getBirthday());
    }

    @Override
    public UserDb add(UserDb user) {
        this.addUserToDb(user);
        long userId = super.getLastIdFromDataBase(tableName);
        return super.get(sqlReceiveFilmById, new BeanPropertyRowMapper<>(UserDb.class), userId);
    }

    @Override
    public UserDb update(UserDb object) {
        return null;
    }

    @Override
    public UserDb get(long id) {
        return null;
    }

    @Override
    public List<UserDb> getList() {
        return null;
    }

    @Override
    public void testMethod() {

    }

    @Override
    public void addFriend(long id, long friendId) {

    }

    @Override
    public void removeFriend(long id, long friendId) {

    }

    @Override
    public List<UserDb> getFriendList(long id) {
        return null;
    }

    @Override
    public List<UserDb> getCommonFriendList(long id, long otherId) {
        return null;
    }
}