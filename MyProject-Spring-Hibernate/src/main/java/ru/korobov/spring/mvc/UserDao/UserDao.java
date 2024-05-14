package ru.korobov.spring.mvc.UserDao;

import ru.korobov.spring.mvc.model.User;

import java.util.List;

public interface UserDao {

    User getUserById(Long id);
    void saveUser(User user);
    void updateUser(User user);
    void deleteUser(Long id);
    List<User> getAllUsers();

}
