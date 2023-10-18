package com.wallet.infrastructure.db.statements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PreparedStatementTransaction {
    private static PreparedStatementTransaction instance = null;

    private PreparedStatementTransaction() {}

    public static PreparedStatementTransaction getInstance() {
        if (instance == null) { instance = new PreparedStatementTransaction(); }
        return instance;
    }

    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     * [1 - UUID.toString() id]
     * [2 - long wallet_id]
     * [3 - String type]
     * [4 - String status]
     * [5 - BigDecimal money_amount
     */
    public PreparedStatement insertTransaction(Connection connection) throws SQLException {
        return connection.prepareStatement("INSERT INTO walletservice.\"Transaction\" VALUES (?, ?, ?, ?, ?, current_timestamp);");
    }

    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     * [1 - String status]
     */
    public PreparedStatement updateTransaction(Connection connection) throws SQLException {
        return connection.prepareStatement("""
                UPDATE walletservice."Transaction" SET
                status = ?;
                """);
    }

    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     * [1 - UUID.toString() id]
     */
    public PreparedStatement deleteTransaction(Connection connection) throws SQLException {
        return connection.prepareStatement("DELETE FROM walletservice.\"Transaction\" WHERE id = ?;");
    }

    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     * [1 - UUID.toString() id]
     */
    public PreparedStatement getTransactionById(Connection connection) throws SQLException {
        return connection.prepareStatement("SELECT * FROM walletservice.\"Transaction\" WHERE id = ?;");
    }

    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     * [1 - long wallet_id]
     */
    public PreparedStatement getTransactionHistoryOfWallet(Connection connection) throws SQLException {
        return connection.prepareStatement("SELECT * FROM walletservice.\"Transaction\" WHERE wallet_id = ?");
    }
}
