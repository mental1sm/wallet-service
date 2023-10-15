package com.schemaexec;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;

public class SchemaExecutor {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/testwalletdb";
        String username = "postgres";
        String password = "123321";

        try(Connection conn = DriverManager.getConnection(jdbcUrl, username, password)) {
            Statement statement = conn.createStatement();
            statement.execute("CREATE SCHEMA IF NOT EXISTS liquischema;");
        } catch (SQLException e) {
            System.out.printf("\n[SQL]: %s", e.getSQLState());
        }
    }
}
