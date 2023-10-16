package com.wallet.infrastructure.db.liquibase;

/**
 * Выполняет миграцию для БД
 */
public interface Migration {
    /**
     * Выполняет миграцию для БД
     */
    static void migrate() {}
}
