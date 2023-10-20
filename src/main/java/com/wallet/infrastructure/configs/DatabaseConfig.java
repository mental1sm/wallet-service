package com.wallet.infrastructure.configs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Доступ к .properties файлу с настройками БД и миграции (bd/liquibase.properties)
*/
public class DatabaseConfig implements Config {
    private Properties property;
    private static DatabaseConfig instance = null;
    private DatabaseConfig() {
        Properties property = new Properties();

        try (InputStream istream = getClass().getClassLoader().getResourceAsStream("db/liquibase.properties"); ) {
            property.load(istream);
            this.property = property;
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private DatabaseConfig(String jdbcUrl, String username, String password) {
        Properties properties = new Properties();
        properties.put("url", jdbcUrl);
        properties.put("username", username);
        properties.put("password", password);
        this.property = properties;
    }

    /**
     * Получение синглтона
    */
    public static DatabaseConfig getInstance() {
        if (instance == null) { instance = new DatabaseConfig(); }
        return instance;
    }

    /**
     * Получение синглтона (TESTS ONLY)
     */
    public static DatabaseConfig getInstance(String jdbcUrl, String username, String password) {
        if (instance == null) { instance = new DatabaseConfig(jdbcUrl, username, password); }
        return instance;
    }

    /**
     * Возвращает значение property по ключу
    */
    public String getProperty(String propertyName) {
        return property.getProperty(propertyName);
    }
}
