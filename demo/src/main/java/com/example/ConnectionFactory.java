package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class ConnectionFactory {
    public static Connection getConnection() {
        try {
            String url = System.getenv("DB_URL");
            String username = System.getenv("DB_USER");
            String password = System.getenv("DB_PASSWORD");
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.getStackTrace();
            System.exit(0);
            return null;
        }
    }
}
