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
     * [1 - long player_id]
     * [2 - String action]
     * [3 - String info_level]
     */
    public PreparedStatement saveLog(Connection connection) throws SQLException {
        return connection.prepareStatement("INSERT INTO logs.\"Log\" VALUES (DEFAULT, current_timestamp, ?, ?)");
    }

    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     * [1 - long id]
     */
    public PreparedStatement getAmountOfImportantLogs(Connection connection) throws SQLException {
        return connection.prepareStatement("""
            SELECT count(id) AS log_count FROM logs."Log" GROUP BY id 
            HAVING (info_level = 'WARNING' OR info_level='ERROR');
    """);
    }

    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     * [1 - long OFFSET]
     * [2 - long LIMIT]
     */
    public PreparedStatement getImportantLogs(Connection connection) throws SQLException {
        return connection.prepareStatement("""
                SELECT * FROM logs."Log" WHERE
                (info_level = 'WARNING' OR info_level = 'ERROR')
                LIMIT ? OFFSET ?;
                """);
    }

    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     */
    public PreparedStatement getAllLogs(Connection connection) throws SQLException {
        return connection.prepareStatement("""
                SELECT * FROM logs."Log";
                """);
    }
}
