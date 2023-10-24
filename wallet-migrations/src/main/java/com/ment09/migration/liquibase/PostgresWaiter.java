package com.ment09.migration.liquibase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresWaiter {
    public static void waitUntilDatabaseIsAvailable(int maxAttempts, int waitIntervalMillis) {
        int attempt = 0;
        while (attempt < maxAttempts) {
            try {
                Connection connection = SetupConnection.getConnection();
                connection.close();
                System.out.println("Database is available.");
                return;
            } catch (SQLException e) {
                System.out.println("Database is not available. Waiting...");
                try {
                    Thread.sleep(waitIntervalMillis);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                attempt++;
            }
        }
        System.out.println("Database did not become available within the given time.");
    }
}
