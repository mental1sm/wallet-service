package com.ment09.migration.liquibase;

import com.ment09.migration.configs.Config;
import com.ment09.migration.configs.DatabaseConfig;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Выполняет миграцию для БД Postgres
*/
public class PostgresMigration implements Migration {

    public static void migrate() {
        CreateServiceSchema.createSchema();
        CreateKeycloakSchema.createSchema();
        Config config = DatabaseConfig.getInstance();
        try (Connection connection = SetupConnection.getConnection();) {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            database.setDefaultSchemaName(config.getProperty("defaultSchemaName"));
            Liquibase liquibase = new Liquibase("db/changelog/changelog.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update("Initial migration");
        } catch (SQLException | LiquibaseException e) {
            e.printStackTrace();
        }
    }
}
