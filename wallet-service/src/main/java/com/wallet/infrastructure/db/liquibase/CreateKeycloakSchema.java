package com.wallet.infrastructure.db.liquibase;

import com.wallet.infrastructure.db.SetupConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateKeycloakSchema {
    public static void createSchema() {
        String SQL = "CREATE SCHEMA IF NOT EXISTS keycloak;";
        try (Connection connection = SetupConnection.getConnection();) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(SQL);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
