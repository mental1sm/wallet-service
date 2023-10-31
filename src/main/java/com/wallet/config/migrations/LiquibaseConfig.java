package com.wallet.config.migrations;

import com.wallet.config.DatabaseConfig;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfig {

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource, DatabaseConfig databaseConfig) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setDefaultSchema(databaseConfig.getDefaultSchema());
        liquibase.setChangeLog(databaseConfig.getChangelogPath());
        return liquibase;
    }
}
