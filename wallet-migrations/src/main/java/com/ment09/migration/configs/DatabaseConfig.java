package com.ment09.migration.configs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Доступ к .properties файлу с настройками БД и миграции (bd/liquibase.properties)
*/
public class DatabaseConfig implements Config {
    private Properties properties;
    private static DatabaseConfig instance = null;
    private DatabaseConfig(String url, String username, String password) {
        properties = new Properties();

        try (InputStream istream = getClass().getClassLoader().getResourceAsStream("db/liquibase.properties"); ) {
            properties.load(istream);
        } catch (IOException e){
            e.printStackTrace();
        }

        properties.replace("url", url);
        properties.replace("username", username);
        properties.replace("password", password);

    }


    /**
     * Получение синглтона
    */
    public static DatabaseConfig getInstance() {
        return instance;
    }

    /**
     * Инициализация
     */
    public static void init(String url, String username, String password) {
        if (instance == null) { instance = new DatabaseConfig(url, username, password); }
    }


    /**
     * Возвращает значение property по ключу
    */
    public String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }
}
