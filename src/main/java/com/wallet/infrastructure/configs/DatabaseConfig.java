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

    public static DatabaseConfig getInstance() {
        if (instance == null) { instance = new DatabaseConfig(); }
        return instance;
    }

    /**
     * Возвращает значение property по ключу
    */
    public String getProperty(String propertyName) {
        return property.getProperty(propertyName);
    }
}
