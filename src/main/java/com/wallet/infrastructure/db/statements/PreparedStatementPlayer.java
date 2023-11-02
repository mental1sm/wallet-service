package com.wallet.infrastructure.db.statements;

import com.wallet.domain.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class PreparedStatementPlayer {
    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     *  [1 - String login]
     *  [2 - String password]
     *  [3 - String name]
     *  [4 - String surname]
    */
    public PreparedStatement insertPlayer(Connection connection, User pl) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO walletservice.\"Player\" VALUES (DEFAULT, ?, ?, ?, ?, ?);");
        preparedStatement.setString(1, pl.getLogin());
        preparedStatement.setString(2, pl.getPassword());
        preparedStatement.setString(3, pl.getName());
        preparedStatement.setString(4, pl.getSurname());
        preparedStatement.setString(5, pl.getEmail());
        return preparedStatement;
    }

    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     * [1 - long id]
     */
    public PreparedStatement deletePlayer(Connection connection, User pl) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM walletservice.\"Player\" WHERE login = ?;");
        preparedStatement.setString(1, pl.getLogin());
        return preparedStatement;
    }

    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     * [1 - String password]
     * [2 - String name]
     * [3 - String surname]
     * [4 - int permission_id]
     */
    public PreparedStatement updatePlayer(Connection connection, User pl) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("""
                UPDATE walletservice."Player" SET 
                password = ?,
                name = ?,
                surname = ?
                WHERE login = ?;
                """);
        preparedStatement.setString(1, pl.getPassword());
        preparedStatement.setString(2, pl.getName());
        preparedStatement.setString(3, pl.getSurname());
        preparedStatement.setString(4, pl.getLogin());
        return preparedStatement;
    }

    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     * [1 - int id]
     */
    public PreparedStatement getPlayerById(Connection connection, long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("""

                SELECT * FROM walletservice."Player"
                WHERE id = ?
                """);
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }

    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     * [1 - String login]
     */
    public PreparedStatement getPlayerByLogin(Connection connection, String pLogin) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("""

                SELECT * FROM walletservice."Player"
                WHERE login = ?
                """);
        preparedStatement.setString(1, pLogin);
        return preparedStatement;
    }

    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     * [1 - String email]
     */
    public PreparedStatement getPlayerByEmail(Connection connection, String pLogin) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("""

                SELECT * FROM walletservice."Player"
                WHERE email = ?
                """);
        preparedStatement.setString(1, pLogin);
        return preparedStatement;
    }

}
