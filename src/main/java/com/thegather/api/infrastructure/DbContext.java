package com.thegather.api.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbContext {
    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl", "rm96828", "Julia24");
            System.out.println("Connected to Database.");
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to database", e.getCause());
        }

    }
}
