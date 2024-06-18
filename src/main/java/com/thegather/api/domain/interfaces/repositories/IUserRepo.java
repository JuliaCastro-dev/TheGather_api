package com.thegather.api.domain.interfaces.repositories;

import com.thegather.api.domain.entities.User;

import java.util.List;

public interface IUserRepo {
    User createUser(User user);
    boolean deleteUser(Long id);
    List<User> getAllUsers();
    int updateUser(User user);
    User getUserById(long id);

}
