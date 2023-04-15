package ru.yandex.practicum.filmorate.service.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.user.UserDb;
import ru.yandex.practicum.filmorate.service.interfaces.UserDao;

import java.util.List;

import static ru.yandex.practicum.filmorate.message.UserLogMessage.*;

@Slf4j
@Service
public class UserDaoService extends AbstractDao<UserDb> implements UserDao<UserDb> {
    private final String tableName = "USER_DB";
    private final String sqlReceiveUserList = "SELECT " +
            "ID, NAME, EMAIL, LOGIN, BIRTHDAY " +
            "FROM USER_DB ";
    private final String sqlReceiveFilmById = String.join(" ", sqlReceiveUserList, "WHERE ID = ?");
    private final String userAddSql = "INSERT INTO USER_DB (NAME, EMAIL, LOGIN, BIRTHDAY) VALUES(?, ?, ?, ?)";
    private final String friendAddSql = "INSERT INTO FRIENDS (FRIEND_ONE, FRIEND_TWO, FRIENDSHIP) VALUES(?, ?, ?)";
    private final String mpaAddSql = "INSERT INTO FILM_MPA (MPA_ID, FILM_ID) VALUES(?, ?)";
    private final String genreAddSql = "INSERT INTO FILM_GENRE (GENRE_ID, FILM_ID) VALUES(?, ?)";
    private final String userUpdateSql = "UPDATE USER_DB " +
            "SET NAME = ?, EMAIL = ?, LOGIN = ?, BIRTHDAY = ? " +
            "WHERE ID = ? ";
    private final String rateUpdateSql = "UPDATE RATE SET RATE = ? WHERE FILM_ID = ?";
    private final String mpaUpdateSql = "UPDATE FILM_MPA SET MPA_ID = ? WHERE FILM_ID = ?";
    private final String genreDeleteSql = "DELETE FROM FILM_GENRE WHERE FILM_ID = ?";

    @Autowired
    protected UserDaoService(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

//    private void addUserToDb(UserDb user) {
//        jdbcTemplate.update(userAddSql,
//                user.getName(),
//                user.getEmail(),
//                user.getLogin(),
//                user.getBirthday());
//    }

    @Override
    public UserDb add(UserDb user) {
//        this.addUserToDb(user);
        jdbcTemplate.update(userAddSql,
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getBirthday());
        long userId = super.getLastIdFromDataBase(tableName);
        log.info(USER_ADDED.message(), user.getLogin());
        return super.get(sqlReceiveFilmById, new BeanPropertyRowMapper<>(UserDb.class), userId);
    }

    @Override
    public UserDb update(UserDb user) {
        jdbcTemplate.update(userUpdateSql,
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getBirthday(),
                user.getId());
        log.info(USER_UPDATED.message(), user.getId(), user.getLogin());
        return super.get(sqlReceiveFilmById, new BeanPropertyRowMapper<>(UserDb.class), user.getId());
    }

    @Override
    public UserDb get(long id) {
        log.info(GET_USER.message(), id);
        return super.get(sqlReceiveFilmById, new BeanPropertyRowMapper<>(UserDb.class), id);
    }

    @Override
    public List<UserDb> getList() {
        log.info(GET_USER_LIST.message());
        return super.getList(sqlReceiveUserList, new BeanPropertyRowMapper<>(UserDb.class));
    }

    @Override
    public void addFriend(long id, long friendId) {
        jdbcTemplate.update(friendAddSql,
                Math.min(id, friendId),
                Math.max(id, friendId),
                true);
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

    @Override
    public void testMethod() {

    }
}