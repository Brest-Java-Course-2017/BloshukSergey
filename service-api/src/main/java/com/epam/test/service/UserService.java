package com.epam.test.service;

import com.epam.test.dao.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(Integer userId);

    Integer addUser(User user);

    void updateUser(User user);

    void deleteUser(Integer userId);
}
