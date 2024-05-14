package ru.korobov.spring.mvc.service;

import ru.korobov.spring.mvc.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    void saveUser(User user);
    void deleteUser(Long userId);
    void updateUser(User user);
    User getUserById(Long userId);
}
