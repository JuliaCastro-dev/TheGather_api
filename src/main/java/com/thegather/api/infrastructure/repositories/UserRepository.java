package com.thegather.api.infrastructure.repositories;

import com.thegather.api.domain.entities.User;
import com.thegather.api.domain.interfaces.repositories.IUserRepo;
import com.thegather.api.domain.interfaces.dao.IUserDAO;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class UserRepository implements IUserRepo {
    private final IUserDAO userDAO;
    public UserRepository(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public boolean deleteUser(Long id) {
        return userDAO.deleteUser(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public int updateUser(User user) {
        return  userDAO.updateUser(user);
    }

    @Override
    public User getUserById(long id) {
        return userDAO.getUserById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }


    @Override
    public User createUser(User user) {
        return userDAO.createUser(user);
    }

}
