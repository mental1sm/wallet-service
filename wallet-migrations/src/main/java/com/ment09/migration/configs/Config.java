package com.ment09.migration.configs;

/**
 * Доступ к .properties файлам
 */
public interface Config {
    String getProperty(String propertyName);
}
