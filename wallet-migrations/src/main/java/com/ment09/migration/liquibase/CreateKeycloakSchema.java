package com.ment09.migration.liquibase;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateKeycloakSchema {
    public static void createSchema() {
        String SQL = "CREATE SCHEMA IF NOT EXISTS keycloak;";
        try (Connection connection = SetupConnection.getConnection();) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(SQL);
        } catch (SQLException e) {}
    }
}
