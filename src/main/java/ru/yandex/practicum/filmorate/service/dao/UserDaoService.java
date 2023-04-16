package ru.yandex.practicum.filmorate.service.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.user.FriendNotFoundException;
import ru.yandex.practicum.filmorate.model.user.UserDb;
import ru.yandex.practicum.filmorate.service.interfaces.UserDao;

import java.util.List;

import static ru.yandex.practicum.filmorate.message.UserLogMessage.*;

@Slf4j
@Service
public class UserDaoService extends AbstractDao<UserDb> implements UserDao<UserDb> {
    private final String tableName = "USER_DB";
    private final String sqlReceiveList = "SELECT ID, NAME, EMAIL, LOGIN, BIRTHDAY FROM USER_DB";
    private final String sqlReceiveById = String.join(" ", sqlReceiveList, "WHERE ID = ?");
    private final String existsFriends = "EXISTS (SELECT * FROM FRIENDS f WHERE FRIEND_ONE = %d AND FRIEND_TWO = ID)";
    private final String sqlReceiveFriendList =
            String.join(
                    " ",
                    sqlReceiveList,
                    "WHERE",
                    existsFriends);
    private final String sqlReceiveCommonFriendList =
            String.join(
                    " ",
                    sqlReceiveFriendList,
                    "AND",
                    existsFriends);
    private final String userAddSql = "INSERT INTO USER_DB (NAME, EMAIL, LOGIN, BIRTHDAY) VALUES(?, ?, ?, ?)";
    private final String friendAddSql = "INSERT INTO FRIENDS (FRIEND_ONE, FRIEND_TWO) VALUES(?, ?)";
    private final String friendDeleteSql = "DELETE FROM FRIENDS WHERE FRIEND_ONE = ? AND FRIEND_TWO = ?";
    private final String userUpdateSql = "UPDATE USER_DB " +
            "SET NAME = ?, EMAIL = ?, LOGIN = ?, BIRTHDAY = ? " +
            "WHERE ID = ? ";

    @Autowired
    protected UserDaoService(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public UserDb add(UserDb user) {
        jdbcTemplate.update(
                userAddSql,
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getBirthday());
        long userId = super.getLastIdFromDataBase(tableName);
        log.info(USER_ADDED.message(), user.getLogin());
        return super.get(sqlReceiveById, new BeanPropertyRowMapper<>(UserDb.class), userId);
    }

    @Override
    public UserDb update(UserDb user) {
        jdbcTemplate.update(
                userUpdateSql,
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getBirthday(),
                user.getId());
        log.info(USER_UPDATED.message(), user.getId(), user.getLogin());
        return super.get(sqlReceiveById, new BeanPropertyRowMapper<>(UserDb.class), user.getId());
    }

    @Override
    public UserDb get(long id) {
        log.info(GET_USER.message(), id);
        return super.get(sqlReceiveById, new BeanPropertyRowMapper<>(UserDb.class), id);
    }

    @Override
    public List<UserDb> getList() {
        log.info(GET_USER_LIST.message());
        return super.getList(sqlReceiveList, new BeanPropertyRowMapper<>(UserDb.class));
    }

    @Override
    public void addFriend(long id, long friendId) {
        try {
            jdbcTemplate.update(
                    friendAddSql,
                    (id), friendId);
            log.info(USER_FRIEND_ADDED.message(), id, friendId);
        } catch (DuplicateKeyException e) {
            log.warn(WARN_FRIENDSHIP_ALREADY_EXIST.message(), id, friendId);
        } catch (DataIntegrityViolationException e) {
            throw new FriendNotFoundException(id, friendId);
        }
    }

    @Override
    public void removeFriend(long id, long friendId) {
        jdbcTemplate.update(
                friendDeleteSql,
                (id), friendId);
        log.info(USER_FRIEND_REMOVED.message(), id, friendId);
    }

    @Override
    public List<UserDb> getFriendList(long id) {
        log.info(GET_USER_FRIEND_LIST.message(), id);
        return super.getList(
                String.format(sqlReceiveFriendList, id),
                new BeanPropertyRowMapper<>(UserDb.class));
    }

    @Override
    public List<UserDb> getCommonFriendList(long id, long friendId) {
        log.info(GET_USER_COMMON_FRIEND_LIST.message(), id, friendId);
        return super.getList(
                String.format(sqlReceiveCommonFriendList, id, friendId),
                new BeanPropertyRowMapper<>(UserDb.class));
    }

    @Override
    public void testMethod() {
    }
}