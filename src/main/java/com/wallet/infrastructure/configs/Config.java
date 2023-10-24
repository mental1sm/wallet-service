package com.wallet.infrastructure.configs;

/**
 * Доступ к Конфиг файлам
 */
public interface Config {
    String getProperty(String propertyName);
}
