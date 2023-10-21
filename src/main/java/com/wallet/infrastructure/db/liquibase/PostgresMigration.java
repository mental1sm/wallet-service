package com.wallet.infrastructure.db.liquibase;

import com.wallet.infrastructure.configs.Config;
import com.wallet.infrastructure.configs.DatabaseConfig;
import com.wallet.infrastructure.db.SetupConnection;
import com.wallet.utility.exceptions.PlayerIsNotExistsException;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Выполняет миграцию для БД Postgres
*/
public class PostgresMigration implements Migration {

    public static void migrate() throws PlayerIsNotExistsException, SQLException {
        CreateServiceSchema.createSchema();
        Config config = DatabaseConfig.getInstance();
        SetupConnection.withConnection(connection -> {
            Database database = null;
            try {
                database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
                database.setDefaultSchemaName(config.getProperty("defaultSchemaName"));
                Liquibase liquibase = new Liquibase("/db/changelog/changelog.xml", new ClassLoaderResourceAccessor(), database);
                liquibase.update("Initial migration");
            } catch (LiquibaseException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
