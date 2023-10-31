package com.wallet.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import java.util.Map;


@Configuration
@Data
public class DatabaseConfig {
    private String driver;
    private String url;
    private String username;
    private String password;
    private String defaultSchema;
    private String changelogPath;

    @Autowired
    public DatabaseConfig(@Qualifier("yamlProperties") Map<String, Object> yamlProperties) throws JsonProcessingException {
        Map<String, Object> yaml = (Map<String, Object>) yamlProperties.get("yamlProperties");
        Map<String, Object> spring = (Map<String, Object>) yaml.get("spring");
        Map<String, Object> liquibase = (Map<String, Object>) spring.get("liquibase");
        Map<String, Object> dataSource = (Map<String, Object>) spring.get("datasource");

        driver = (String) dataSource.get("driver-class-name");
        url = (String) dataSource.get("url");
        username = (String) dataSource.get("username");
        password = dataSource.get("password").toString();
        defaultSchema = (String) liquibase.get("schema");
        changelogPath = (String) liquibase.get("change-log");
    }

}
