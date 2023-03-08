package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {
    private final Map<Integer, User> userDatabase;
    private int idCounter;

    public UserRepository() {
        this.userDatabase = new HashMap<>();
    }

    public boolean isContains(User user) {
        return userDatabase.containsKey(user.getId());
    }

    public User addNewUser(User user) {
        user.setId(++idCounter);
        userDatabase.put(user.getId(), user);
        return userDatabase.get(user.getId());
    }

    public User updateUser(User user) {
        userDatabase.put(user.getId(), user);
        return userDatabase.get(user.getId());
    }

    public List<User> getUserList() {
        return new ArrayList<>(userDatabase.values());
    }

    public void deleteAll() {
        userDatabase.clear();
        idCounter = 0;
    }
}