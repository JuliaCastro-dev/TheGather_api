package com.thegather.api.infrastructure.dao;

import com.thegather.api.domain.entities.User;
import com.thegather.api.domain.interfaces.dao.IUserDAO;
import com.thegather.api.infrastructure.DbContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDAO implements IUserDAO {
    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

    @Override
    public User createUser(User user) {
        String sql = "INSERT INTO USERS (NAME, EMAIL, PASSWORD, ADDRESS, PHONE, CEP, CPF, OFFICE, COMPANY_ID) VALUES (?,?,?,?,?,?,?,?,?)";

        try (Connection connection = DbContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getAddress());
            statement.setString(5, user.getPhone());
            statement.setString(6, user.getCEP());
            statement.setString(7, user.getCPF());
            statement.setInt(8, user.getOffice());
            statement.setInt(9, user.getCompany_id());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 1) {
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        long userId = resultSet.getLong(1);
                        user.setId(userId);
                    }
                }
                return user;
            } else {
                return null;
            }

        } catch (SQLException e) {
            logger.error("Error creating user: {}", user, e);
            return null;
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM USERS";

        try (Connection connection = DbContext.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                User user = new User(resultSet.getLong("ID"),
                        resultSet.getString("NAME"),
                        resultSet.getString("EMAIL"),
                        resultSet.getString("PASSWORD"),
                        resultSet.getString("ADDRESS"),
                        resultSet.getString("PHONE"),
                        resultSet.getString("CEP"),
                        resultSet.getString("CPF"),
                        resultSet.getInt("OFFICE"),
                        resultSet.getInt("COMPANY_ID"));
                users.add(user);
            }

        } catch (SQLException e) {
            logger.error("Error retrieving all users", e);
        }

        return users;
    }

    @Override
    public int updateUser(User user) {
        String sql = "UPDATE USERS SET NAME = ?, EMAIL = ?, PASSWORD = ?, ADDRESS = ?, PHONE = ?, CEP = ?, CPF = ?, OFFICE = ?, COMPANY_ID = ? WHERE ID = ?";

        try (Connection connection = DbContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getAddress());
            statement.setString(5, user.getPhone());
            statement.setString(6, user.getCEP());
            statement.setString(7, user.getCPF());
            statement.setInt(8, user.getOffice());
            statement.setInt(9, user.getCompany_id());
            statement.setLong(10, user.getId());

            return statement.executeUpdate();

        } catch (SQLException e) {
            logger.error("Error updating user: {}", user, e);
            return 0;
        }
    }

    @Override
    public User getUserById(long id) {
        String sql = "SELECT * FROM USERS WHERE ID = ?";

        try (Connection connection = DbContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(resultSet.getLong("ID"),
                            resultSet.getString("NAME"),
                            resultSet.getString("EMAIL"),
                            resultSet.getString("PASSWORD"),
                            resultSet.getString("ADDRESS"),
                            resultSet.getString("PHONE"),
                            resultSet.getString("CEP"),
                            resultSet.getString("CPF"),
                            resultSet.getInt("OFFICE"),
                            resultSet.getInt("COMPANY_ID"));
                }
            }

        } catch (SQLException e) {
            logger.error("Error retrieving user by ID: {}", id, e);
        }

        return null;
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM USERS WHERE EMAIL = ?";

        try (Connection connection = DbContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(resultSet.getLong("ID"),
                            resultSet.getString("NAME"),
                            resultSet.getString("EMAIL"),
                            resultSet.getString("PASSWORD"),
                            resultSet.getString("ADDRESS"),
                            resultSet.getString("PHONE"),
                            resultSet.getString("CEP"),
                            resultSet.getString("CPF"),
                            resultSet.getInt("OFFICE"),
                            resultSet.getInt("COMPANY_ID"));
                }
            }

        } catch (SQLException e) {
            logger.error("Error retrieving user by email: {}", email, e);
        }

        return null;
    }

    @Override
    public boolean deleteUser(Long id) {
        String sql = "DELETE FROM USERS WHERE ID = ?";

        try (Connection connection = DbContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected == 1;

        } catch (SQLException e) {
            logger.error("Error deleting user with ID: {}", id, e);
            return false;
        }
    }
}
