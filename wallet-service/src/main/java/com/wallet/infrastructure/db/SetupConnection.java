package com.wallet.infrastructure.db;

import com.wallet.dao.log.LogDaoImpl;
import com.wallet.dao.player.PlayerDaoImpl;
import com.wallet.domain.entities.Log;
import com.wallet.infrastructure.configs.Config;
import com.wallet.infrastructure.configs.DatabaseConfig;
import com.wallet.services.loggerService.LoggerService;
import com.wallet.utility.exceptions.PlayerIsNotExistsException;
import liquibase.exception.LiquibaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Абстрагированная установка соединения с автоматическим закрытием ресурсов
*/
public class SetupConnection {
    private static final LoggerService logger = LoggerService.getInstance(new LogDaoImpl(), new PlayerDaoImpl());
    /**
     * Абстрагированная установка соединения с автоматическим закрытием ресурсов
     */
    public static void withConnection(ConnectionAction action) throws SQLException, PlayerIsNotExistsException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found: db driver");
        }
        Config config = DatabaseConfig.getInstance();
        String URL = config.getProperty("url");
        String user = config.getProperty("username");
        String password = config.getProperty("password");
        Connection connection = DriverManager.getConnection(URL, user, password);
        action.execute(connection);
        connection.close();
    }

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
