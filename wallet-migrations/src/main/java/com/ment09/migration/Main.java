package com.ment09.migration;

import com.ment09.migration.configs.DatabaseConfig;
import com.ment09.migration.liquibase.PostgresMigration;
import com.ment09.migration.liquibase.PostgresWaiter;

public class Main {
    public static void main(String[] args) {
        String url = args[0];
        String username = args[1];
        String password = args[2];
        DatabaseConfig.init(url, username, password);
        PostgresWaiter.waitUntilDatabaseIsAvailable(10, 2000);
        PostgresMigration.migrate();
    }
}
