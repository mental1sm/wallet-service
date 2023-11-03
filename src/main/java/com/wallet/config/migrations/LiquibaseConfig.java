package com.wallet.config.migrations;

import com.wallet.config.DatabaseConfig;
import liquibase.integration.spring.SpringLiquibase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Осуществляет миграции
 */
@Configuration
@RequiredArgsConstructor
@ComponentScan("com.wallet.config.migrations")
@ComponentScan("com.wallet.infrastructure.db")
@Slf4j
public class LiquibaseConfig {
    private final CreateKeycloakSchema createKeycloakSchema;
    private final CreateServiceSchema createServiceSchema;

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource, DatabaseConfig databaseConfig) {
        SpringLiquibase liquibase = new SpringLiquibase();
        try {
            createKeycloakSchema.createSchema();
            createServiceSchema.createSchema();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        liquibase.setDataSource(dataSource);
        liquibase.setDefaultSchema(databaseConfig.getDefaultSchema());
        liquibase.setChangeLog(databaseConfig.getChangelogPath());
        return liquibase;
    }
}
