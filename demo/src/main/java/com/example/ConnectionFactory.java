package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class ConnectionFactory {
    public static Connection getConnection() {
        try {
            String url = System.getenv("URL");
            String username = System.getenv("USERNAME");
            String password = System.getenv("PASSWORD");
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.getStackTrace();
            System.exit(0);
            return null;
        }
    }
}
