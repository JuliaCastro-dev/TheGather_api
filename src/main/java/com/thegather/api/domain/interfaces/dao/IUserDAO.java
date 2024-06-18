package com.thegather.api.domain.interfaces.dao;

import com.thegather.api.domain.entities.User;

import java.util.List;

public interface IUserDAO {
    User createUser(User user);
    boolean deleteUser(Long id);
    List<User> getAllUsers();
    int updateUser(User user);
    User getUserById(long id);
}
