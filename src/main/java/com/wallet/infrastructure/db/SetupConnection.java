package com.wallet.infrastructure.db;

import com.wallet.infrastructure.configs.Config;
import com.wallet.infrastructure.configs.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Абстрагированная установка соединения с автоматическим закрытием ресурсов
*/
public class SetupConnection {
    /**
     * Абстрагированная установка соединения с автоматическим закрытием ресурсов
     */
    public static void withConnection(ConnectionAction action) {
        Config config = DatabaseConfig.getInstance();
        String URL = config.getProperty("url");
        String user = config.getProperty("username");
        String password = config.getProperty("password");
        try (Connection connection = DriverManager.getConnection(URL, user, password); ) {
            action.execute(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
