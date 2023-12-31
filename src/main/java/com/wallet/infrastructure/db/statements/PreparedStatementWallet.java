package com.wallet.infrastructure.db.statements;

import com.wallet.domain.entities.User;
import com.wallet.domain.entities.Wallet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class PreparedStatementWallet {

    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     * [1 - long player_id]
     * [2 - BigDecimal money_amount]
     */
    public PreparedStatement insertWallet(Connection connection, Wallet wallet) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO walletservice.\"Wallet\" VALUES (DEFAULT, ?, ?);");
        preparedStatement.setLong(1, wallet.getPlayerId());
        preparedStatement.setBigDecimal(2, wallet.getMoneyAmount());
        return preparedStatement;
    }

    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     * [1 - long id]
     */
    public PreparedStatement deleteWallet(Connection connection, Wallet wallet) throws SQLException {
        PreparedStatement preparedStatement =  connection.prepareStatement("DELETE FROM walletservice.\"Wallet\" WHERE id = ?;");
        preparedStatement.setLong(1, wallet.getId());
        return preparedStatement;
    }

    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     * [1 - BigDecimal money_amount]
     * [2 - long id]
     */
    public PreparedStatement updateWallet(Connection connection, Wallet wallet) throws SQLException {
        PreparedStatement preparedStatement =  connection.prepareStatement("""
                UPDATE walletservice."Wallet" SET 
                money_amount = ?
                WHERE id = ?;
                """);
        preparedStatement.setBigDecimal(1, wallet.getMoneyAmount());
        preparedStatement.setLong(2, wallet.getId());
        return preparedStatement;
    }

    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     * [1 - long player_id]
     */
    public PreparedStatement getWalletsOfPlayer(Connection connection, User pl) throws SQLException {
        PreparedStatement preparedStatement =  connection.prepareStatement("SELECT * FROM walletservice.\"Wallet\" WHERE player_id = ?;");
        preparedStatement.setLong(1, pl.getId());
        return preparedStatement;
    }

    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     * [1 - long id]
     */
    public PreparedStatement getWalletById(Connection connection, long id) throws SQLException {
        PreparedStatement preparedStatement =  connection.prepareStatement("SELECT * FROM walletservice.\"Wallet\" WHERE id = ?;");
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }
}
