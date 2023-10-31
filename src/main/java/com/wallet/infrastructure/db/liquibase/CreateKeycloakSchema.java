package com.wallet.infrastructure.db.liquibase;

import com.wallet.infrastructure.db.SetupConnection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Component
@RequiredArgsConstructor
public class CreateKeycloakSchema {
    private final SetupConnection setupConnection;
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
