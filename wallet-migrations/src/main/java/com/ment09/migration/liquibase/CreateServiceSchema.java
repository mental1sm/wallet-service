package com.ment09.migration.liquibase;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Создает схему для служебных таблиц инструмента миграции liquibase
 */
class CreateServiceSchema {

    /**
     * Создает схему для служебных таблиц инструмента миграции liquibase
     */
    public static void createSchema() {
        String SQL = "CREATE SCHEMA IF NOT EXISTS changelog;";
        try (Connection connection = SetupConnection.getConnection();) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(SQL);
        } catch (SQLException e) {}
    }
}
