package com.wallet.config.migrations;

import com.wallet.infrastructure.db.SetupConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Создает схему для служебных таблиц Keycloak
*/
@Component
public class CreateKeycloakSchema {
    private final SetupConnection setupConnection;

    @Autowired
    public CreateKeycloakSchema(SetupConnection setupConnection) {
        this.setupConnection = setupConnection;
    }

    /**
     * Создает схему для служебных таблиц Keycloak
     */
    public void createSchema() {
        String SQL = "CREATE SCHEMA IF NOT EXISTS keycloak;";
        try (Connection connection = setupConnection.getConnection();) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(SQL);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
