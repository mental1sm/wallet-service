package com.wallet.infrastructure.configs;

/**
 * Доступ к .properties файлам
 */
public interface Config {
    String getProperty(String propertyName);
}
