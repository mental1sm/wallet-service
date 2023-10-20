package com.wallet.infrastructure.db.liquibase;

import com.wallet.infrastructure.configs.Config;
import com.wallet.infrastructure.configs.DatabaseConfig;
import com.wallet.infrastructure.db.SetupConnection;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Выполняет миграцию для БД Postgres
*/
public class PostgresMigration implements Migration {

    public static void migrate() {
        CreateServiceSchema.createSchema();
        Config config = DatabaseConfig.getInstance();
        SetupConnection.withConnection(connection -> {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            database.setDefaultSchemaName(config.getProperty("defaultSchemaName"));
            Liquibase liquibase = new Liquibase("/db/changelog/changelog.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update("Initial migration");
        });
    }
}
