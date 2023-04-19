//package ru.yandex.practicum.filmorate.service.dao.user;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.dao.DuplicateKeyException;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Service;
//import ru.yandex.practicum.filmorate.exception.user.FriendNotFoundException;
//import ru.yandex.practicum.filmorate.model.user.User;
//import ru.yandex.practicum.filmorate.service.dao.AbstractDao;
//import ru.yandex.practicum.filmorate.service.interfaces.UserDao;
//
//import java.util.List;
//
//import static ru.yandex.practicum.filmorate.message.UserLogMessage.*;
//import static ru.yandex.practicum.filmorate.service.dao.user.UserSql.*;
//
//@Slf4j
//@Service
//public class UserDaoService extends AbstractDao<User> implements UserDao<User> {
//    private static final String TABLE_NAME = "USER_DB";
//
//    @Autowired
//    protected UserDaoService(JdbcTemplate jdbcTemplate) {
//        super(jdbcTemplate);
//    }
//
//    @Override
//    public User add(User user) {
//        jdbcTemplate.update(
//                USER_ADD_SQL.getSql(),
//                user.getName(),
//                user.getEmail(),
//                user.getLogin(),
//                user.getBirthday());
//        long userId = super.getLastIdFromDataBase(TABLE_NAME);
//        log.info(USER_ADDED.message(), user.getLogin());
//        return super.get(SQL_RECEIVE_BY_ID.getSql(), new BeanPropertyRowMapper<>(User.class), userId);
//    }
//
//    @Override
//    public User update(User user) {
//        jdbcTemplate.update(
//                USER_UPDATE_SQL.getSql(),
//                user.getName(),
//                user.getEmail(),
//                user.getLogin(),
//                user.getBirthday(),
//                user.getId());
//        log.info(USER_UPDATED.message(), user.getId(), user.getLogin());
//        return super.get(SQL_RECEIVE_BY_ID.getSql(), new BeanPropertyRowMapper<>(User.class), user.getId());
//    }
//
//    @Override
//    public User get(long id) {
//        log.info(GET_USER.message(), id);
//        return super.get(SQL_RECEIVE_BY_ID.getSql(), new BeanPropertyRowMapper<>(User.class), id);
//    }
//
//    @Override
//    public List<User> getList() {
//        log.info(GET_USER_LIST.message());
//        return super.getList(SQL_RECEIVE_LIST.getSql(), new BeanPropertyRowMapper<>(User.class));
//    }
//
//    @Override
//    public void addFriend(long id, long friendId) {
//        try {
//            jdbcTemplate.update(
//                    FRIEND_ADD_SQL.getSql(),
//                    (id), friendId);
//            log.info(USER_FRIEND_ADDED.message(), id, friendId);
//        } catch (DuplicateKeyException e) {
//            log.warn(WARN_FRIENDSHIP_ALREADY_EXIST.message(), id, friendId);
//        } catch (DataIntegrityViolationException e) {
//            throw new FriendNotFoundException(id, friendId);
//        }
//    }
//
//    @Override
//    public void removeFriend(long id, long friendId) {
//        jdbcTemplate.update(
//                FRIEND_DELETE_SQL.getSql(),
//                (id), friendId);
//        log.info(USER_FRIEND_REMOVED.message(), id, friendId);
//    }
//
//    @Override
//    public List<User> getFriendList(long id) {
//        log.info(GET_USER_FRIEND_LIST.message(), id);
//        return super.getList(
//                String.format(SQL_RECEIVE_FRIEND_LIST.getSql(), id),
//                new BeanPropertyRowMapper<>(User.class));
//    }
//
//    @Override
//    public List<User> getCommonFriendList(long id, long friendId) {
//        log.info(GET_USER_COMMON_FRIEND_LIST.message(), id, friendId);
//        return super.getList(
//                String.format(SQL_RECEIVE_COMMON_FRIEND_LIST.getSql(), id, friendId),
//                new BeanPropertyRowMapper<>(User.class));
//    }
//}