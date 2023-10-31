package com.wallet.infrastructure.db.liquibase;

import com.wallet.config.DatabaseConfig;
import com.wallet.infrastructure.db.SetupConnection;
import com.wallet.utility.exceptions.UserIsNotExistsException;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Выполняет миграцию для БД Postgres
*/
@RequiredArgsConstructor
@Service
public class PostgresMigration implements Migration {

    private final SetupConnection setupConnection;
    private final CreateServiceSchema createServiceSchema;
    private final CreateKeycloakSchema createKeycloakSchema;
   // private final DatabaseConfig databaseConfig;

    public void migrate() throws UserIsNotExistsException, SQLException {
        createServiceSchema.createSchema();
        createKeycloakSchema.createSchema();
            Database database = null;
            try (Connection connection = setupConnection.getConnection()) {
                database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
                database.setDefaultSchemaName("");//databaseConfig.getDefaultSchema());
                Liquibase liquibase = new Liquibase("", new ClassLoaderResourceAccessor(), database);
                liquibase.update("Initial migration");
            } catch (LiquibaseException e) {
                throw new RuntimeException(e);
            }
    }
}
