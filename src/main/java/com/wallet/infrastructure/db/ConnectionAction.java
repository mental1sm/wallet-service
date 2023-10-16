package com.wallet.infrastructure.db;

import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Функциональный интерфейс для подачи лямбда-функций внутрь конструкции открытия соединения с БД.
*/
@FunctionalInterface
public interface ConnectionAction {
    void execute(Connection connection) throws SQLException, LiquibaseException;
}
