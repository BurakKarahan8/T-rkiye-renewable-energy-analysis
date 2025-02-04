package com.burakkarahan.Proje_backend.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private static Connection connection;
    
    @Value("${spring.datasource.url}")
    private String url;
    
    @Value("${spring.datasource.username}")
    private String username;
    
    @Value("${spring.datasource.password}")
    private String password;

    // Private constructor
    private DatabaseConnection() {
        if (instance != null) {
            throw new RuntimeException("Singleton instance zaten mevcut - reflection kullanmayın!");
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    @PostConstruct
    private void initialize() {
        instance = this;
        loadDriver();
    }

    private void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL JDBC Driver bulunamadı", e);
        }
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                synchronized (DatabaseConnection.class) {
                    if (connection == null || connection.isClosed()) {
                        connection = DriverManager.getConnection(url, username, password);
                    }
                }
            }
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Veritabanı bağlantısı oluşturulamadı: " + e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Veritabanı bağlantısı kapatılamadı: " + e.getMessage());
        }
    }
}