package com.thegather.api.infrastructure.dao;

import com.thegather.api.domain.entities.User;
import com.thegather.api.domain.interfaces.dao.IUserDAO;
import com.thegather.api.infrastructure.DbContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.NULL;

@Component
public class UserDAO implements IUserDAO {
    @Override
    public User createUser(User user)   {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DbContext.getConnection();
            String sql = "INSERT INTO USERS (NAME, EMAIL, PASSWORD, ADDRESS, PHONE, CEP, CPF, OFFICE, COMPANY_ID) VALUES (?,?,?,?,?,?,?,?,?)";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    long userId = resultSet.getLong(1);
                    user.setId(userId);
                }
                return user;
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Connection connection = DbContext.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM USERS");
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

        }
        catch (SQLException e) {
            e.printStackTrace();

        }finally {
            return users;
        }
    }

    @Override
    public int updateUser(User user) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DbContext.getConnection();

            String sql = "UPDATE USERS " +
                    "SET NAME = ?, EMAIL = ?, PASSWORD = ?, ADDRESS = ?, PHONE = ?, CEP = ?, CPF = ?, OFFICE = ?, COMPANY_ID = ?" +
                    " WHERE ID = ?";

            statement = connection.prepareStatement(sql);
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

            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public User getUserById(long id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = DbContext.getConnection();
            statement = connection.prepareStatement("SELECT * FROM USERS");
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user =   new User(resultSet.getLong("ID"),
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
        catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User getUserByEmail(String email) {
        User user = null;
        try {
            Connection connection = DbContext.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS WHERE EMAIL = " + email);
            if (resultSet.next()) {
                user =   new User(resultSet.getLong("ID"),
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

        } catch (SQLException e) {
            throw new RuntimeException("Cannot get a user with this email", e.getCause());
        }finally {
            return user;
        }
    }

    @Override
    public boolean deleteUser(Long id) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DbContext.getConnection();

            statement = connection.prepareStatement("delete from USERS where ID = ?");
            statement.setLong(1, id);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 1) {
                return true;
            } else if (rowsAffected == 0) {
                return false;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
