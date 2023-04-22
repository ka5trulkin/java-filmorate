//package ru.yandex.practicum.filmorate.service.dao;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.dao.DuplicateKeyException;
//import org.springframework.stereotype.Service;
//import ru.yandex.practicum.filmorate.exception.user.FriendNotFoundException;
//import ru.yandex.practicum.filmorate.model.user.User;
//import ru.yandex.practicum.filmorate.service.abstracts.AbstractService;
//import ru.yandex.practicum.filmorate.interfaces.storage.Storage;
//import ru.yandex.practicum.filmorate.interfaces.service.UserService;
//
//import java.util.Collection;
//
//import static ru.yandex.practicum.filmorate.message.UserLogMessage.*;
//import static ru.yandex.practicum.filmorate.sql.UserSql.*;
//
//@Slf4j
//@Service
//public class UserDaoService extends AbstractService<User> implements UserService {
//    @Autowired
//    protected UserDaoService(@Qualifier("userStorage") Storage<User> storage) {
//        super(storage);
//    }
//
//    @Override
//    public User add(User user) {
//        log.info(USER_ADDED.message(), user.getLogin());
//        return super.add(user);
//    }
//
//    @Override
//    public User update(User user) {
//        log.info(USER_UPDATED.message(), user.getId(), user.getLogin());
//        return super.update(user);
//    }
//
//    @Override
//    public User get(long id) {
//        log.info(GET_USER.message(), id);
//        return super.get(
//                SQL_RECEIVE_BY_ID.getSql(),
//                id);
//    }
//
//    @Override
//    public Collection<User> getList() {
//        log.info(GET_USER_LIST.message());
//        return super.getList(SQL_RECEIVE_LIST.getSql());
//    }
//
//    @Override
//    public void addFriend(long id, long friendId) {
//        try {
//            super.add(
//                    FRIEND_ADD_SQL.getSql(),
//                    id,
//                    friendId);
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
//        log.info(USER_FRIEND_REMOVED.message(), id, friendId);
//        super.delete(
//                FRIEND_DELETE_SQL.getSql(),
//                id,
//                friendId);
//    }
//
//    @Override
//    public Collection<User> getFriendList(long id) {
//        log.info(GET_USER_FRIEND_LIST.message(), id);
//        return super.getList(
//                SQL_RECEIVE_FRIEND_LIST.getSql(),
//                id);
//    }
//
//    @Override
//    public Collection<User> getCommonFriendList(long id, long friendId) {
//        log.info(GET_USER_COMMON_FRIEND_LIST.message(), id, friendId);
//        return super.getList(
//                SQL_RECEIVE_COMMON_FRIEND_LIST.getSql(),
//                id,
//                friendId);
//    }
//}