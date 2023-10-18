package com.wallet.infrastructure.db.statements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PreparedStatementWallet {
    private static PreparedStatementWallet instance = null;

    private PreparedStatementWallet() {}

    public static PreparedStatementWallet getInstance() {
        if (instance == null) { instance = new PreparedStatementWallet(); }
        return instance;
    }

    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     * [1 - long player_id]
     * [2 - BigDecimal money_amount]
     */
    public PreparedStatement insertWallet(Connection connection) throws SQLException {
        return connection.prepareStatement("INSERT INTO walletservice.\"Wallet\" VALUES (DEFAULT, ?, ?);");
    }

    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     * [1 - long id]
     */
    public PreparedStatement deleteWallet(Connection connection) throws SQLException {
        return connection.prepareStatement("DELETE FROM walletservice.\"Wallet\" WHERE id = ?;");
    }

    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     * [1 - BigDecimal money_amount]
     * [2 - long id]
     */
    public PreparedStatement updateWallet(Connection connection) throws SQLException {
        return connection.prepareStatement("""
                UPDATE walletservice."Wallet" SET 
                money_amount = ?
                WHERE id = ?;
                """);
    }

    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     * [1 - long player_id]
     */
    public PreparedStatement getWalletsOfPlayer(Connection connection) throws SQLException {
        return connection.prepareStatement("SELECT * FROM walletservice.\"Wallet\" WHERE player_id = ?;");
    }

    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     * [1 - long id]
     */
    public PreparedStatement getWalletById(Connection connection) throws SQLException {
        return connection.prepareStatement("SELECT * FROM walletservice.\"Wallet\" WHERE id = ?;");
    }
}
