//package ru.yandex.practicum.filmorate.service.interfaces;
//
//import ru.yandex.practicum.filmorate.model.user.User;
//
//import java.util.List;
//
//public interface UserServiceMostDeleted<T extends User> extends Dao<T> {
//    void addFriend(long id, long friendId);
//
//    void removeFriend(long id, long friendId);
//
//    List<T> getFriendList(long id);
//
//    List<T> getCommonFriendList(long id, long otherId);
//}