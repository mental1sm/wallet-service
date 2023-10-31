package com.ment09.migration.liquibase;

import com.ment09.migration.configs.Config;
import com.ment09.migration.configs.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Абстрагированная установка соединения с автоматическим закрытием ресурсов
*/
public class SetupConnection {
    /**
     * Получение готового соединения
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found: db driver");
        }
        Config config = DatabaseConfig.getInstance();
        String URL = config.getProperty("url");
        String user = config.getProperty("username");
        String password = config.getProperty("password");
        return DriverManager.getConnection(URL, user, password);
    }


}
