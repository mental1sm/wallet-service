package com.wallet.config.migrations;

import com.wallet.infrastructure.db.SetupConnection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Создает схему для служебных таблиц инструмента миграции liquibase
 */
@Component
@RequiredArgsConstructor
public class CreateServiceSchema {

    private final SetupConnection setupConnection;
    /**
     * Создает схему для служебных таблиц инструмента миграции liquibase
     */
    public void createSchema() throws SQLException {
        String SQL = "CREATE SCHEMA IF NOT EXISTS changelog;";
        try (Connection connection = setupConnection.getConnection()) {
            Statement statement = connection.createStatement();
             statement.executeUpdate(SQL);
             statement.close();
        }
    }
}
