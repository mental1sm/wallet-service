package com.wallet.infrastructure.db.liquibase;

import com.wallet.infrastructure.configs.Config;
import com.wallet.infrastructure.configs.DatabaseConfig;
import com.wallet.infrastructure.db.SetupConnection;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;

import java.sql.Connection;
import java.sql.DriverManager;
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
        SetupConnection.withConnection(connection -> {
            Statement statement = connection.createStatement();
            statement.executeUpdate(SQL);
        });
    }
}
