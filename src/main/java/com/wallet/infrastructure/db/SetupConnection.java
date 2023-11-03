package com.wallet.infrastructure.db;

import com.wallet.config.DatabaseConfig;
import com.wallet.dao.user.UserDaoImpl;
import com.wallet.utility.exceptions.UserIsNotExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Абстрагированная установка соединения с автоматическим закрытием ресурсов
*/
@Component
@RequiredArgsConstructor
public class SetupConnection {

    private final DatabaseConfig databaseConfig;
    /**
     * Получение готового соединения
     */
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found: db driver");
        };
        String URL = databaseConfig.getUrl();
        String user = databaseConfig.getUsername();
        String password = databaseConfig.getPassword();
        return DriverManager.getConnection(URL, user, password);
    }


}
