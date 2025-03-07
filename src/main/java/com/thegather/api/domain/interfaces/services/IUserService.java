package com.thegather.api.domain.interfaces.services;

import com.thegather.api.domain.entities.Company;
import com.thegather.api.domain.entities.User;

import java.util.List;

public interface IUserService {
    User createUser(User user);
    boolean deleteUser(Long id);
    List<User> getAllUsers();
    int updateUser(User user);
    User getUserById(long id);
    User getLastUser();
    User getUserByEmail(String email);
}
