package com.thegather.api.application.services;

import com.thegather.api.domain.entities.User;
import com.thegather.api.domain.interfaces.repositories.IUserRepo;
import com.thegather.api.domain.interfaces.services.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService implements IUserService {
    IUserRepo userRepository;
    public UserService(IUserRepo userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getLastUser() {
        return userRepository.getLastUser();
    }

    @Override
    public User createUser(User user) {
        return userRepository.createUser(user);
    }

    @Override
    public boolean deleteUser(Long id) {
        return userRepository.deleteUser(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public int updateUser(User user) {
        return userRepository.updateUser(user);
    }

    @Override
    public User getUserById(long id) {
        return userRepository.getUserById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }
}
