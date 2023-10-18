package com.wallet.infrastructure.db.statements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PreparedStatementPermission {
    private static PreparedStatementPermission instance = null;

    private PreparedStatementPermission() {}

    public static PreparedStatementPermission getInstance() {
        if (instance == null) { instance = new PreparedStatementPermission(); }
        return instance;
    }

    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     * [1 - long id]
     */
    public PreparedStatement getPermissionName(Connection connection) throws SQLException {
        return connection.prepareStatement(
                """
                    SELECT name FROM walletservice."Permission"
                    WHERE id = ?;
                    """
        );
    }

    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     */
    public PreparedStatement getPermissions(Connection connection) throws SQLException {
        return connection.prepareStatement("SELECT * FROM walletservice.\"Permission\" ;");
    }
}
