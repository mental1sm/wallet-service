package com.wallet.infrastructure.db.statements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PreparedStatementLog {
    private static PreparedStatementLog instance = null;

    private PreparedStatementLog() {}

    public static PreparedStatementLog getInstance() {
        if (instance == null) { instance = new PreparedStatementLog(); }
        return instance;
    }

    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     * [1 - String action]
     * [2 - String login]
     * [3 - String description]
     */
    public PreparedStatement saveLog(Connection connection) throws SQLException {
        return connection.prepareStatement("INSERT INTO logs.\"Log\" VALUES (DEFAULT, current_timestamp, ?, ?, ?)");
    }

    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * [1 - String login]
     * @return PreparedStatement объект
     */
    public PreparedStatement getAllLogsOfUser(Connection connection) throws SQLException {
        return connection.prepareStatement("""
                SELECT * FROM logs."Log"
                WHERE login = ?;
                """);
    }
}
