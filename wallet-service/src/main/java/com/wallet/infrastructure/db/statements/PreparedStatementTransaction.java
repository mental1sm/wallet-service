package com.wallet.infrastructure.db.statements;

import com.wallet.domain.entities.Transaction;
import com.wallet.domain.entities.Wallet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

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
    public PreparedStatement insertTransaction(Connection connection, Transaction transaction) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "INSERT INTO walletservice.\"Transaction\" VALUES (?, ?, ?, ?, ?, current_timestamp);"
        );
        preparedStatement.setString(1, transaction.getTransactionId().toString());
        preparedStatement.setLong(2, transaction.getWalletId());
        preparedStatement.setString(3, transaction.getTransactionType().toString());
        preparedStatement.setString(4, transaction.getTransactionStatus().toString());
        preparedStatement.setBigDecimal(5, transaction.getTransactionSum());
        return preparedStatement;
    }

    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     * [1 - String status]
     * [2 - UUID.toString() id]
     */
    public PreparedStatement updateTransaction(Connection connection, Transaction transaction) throws SQLException {
        PreparedStatement preparedStatement =  connection.prepareStatement("""
                UPDATE walletservice."Transaction" SET
                status = ?
                WHERE id = ?;
                """);
        preparedStatement.setString(1, transaction.getTransactionStatus().toString());
        preparedStatement.setString(2, transaction.getTransactionId().toString());
        return preparedStatement;
    }

    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     * [1 - UUID.toString() id]
     */
    public PreparedStatement deleteTransaction(Connection connection, Transaction transaction) throws SQLException {
        PreparedStatement preparedStatement =  connection.prepareStatement(
                "DELETE FROM walletservice.\"Transaction\" WHERE id = ?;"
        );
        preparedStatement.setString(1, transaction.getTransactionId().toString());
        return preparedStatement;
    }

    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     * [1 - UUID.toString() id]
     */
    public PreparedStatement getTransactionById(Connection connection, UUID id) throws SQLException {
        PreparedStatement preparedStatement =  connection.prepareStatement(
                "SELECT * FROM walletservice.\"Transaction\" WHERE id = ?;"
        );
        preparedStatement.setString(1, id.toString());
        return preparedStatement;
    }

    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     * [1 - long wallet_id]
     */
    public PreparedStatement getTransactionHistoryOfWallet(Connection connection, Wallet wallet) throws SQLException {
        PreparedStatement preparedStatement =  connection.prepareStatement(
                "SELECT * FROM walletservice.\"Transaction\" WHERE wallet_id = ?"
        );
        preparedStatement.setLong(1, wallet.getId());
        return preparedStatement;
    }
}
