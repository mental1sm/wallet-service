package com.wallet.infrastructure.db.statements;

import com.wallet.entities.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PreparedStatementPlayer {
    private static PreparedStatementPlayer instance = null;

    private PreparedStatementPlayer() {}

    public static PreparedStatementPlayer getInstance() {
        if (instance == null) { instance = new PreparedStatementPlayer(); }
        return instance;
    }
    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     *  [1 - String login]
     *  [2 - String password]
     *  [3 - String name]
     *  [4 - String surname]
    */
    public PreparedStatement insertPlayer(Connection connection, Player pl) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO walletservice.\"Player\" VALUES (DEFAULT, ?, ?, ?, ?, 3);");
        preparedStatement.setString(1, pl.getLogin());
        preparedStatement.setString(2, pl.getPassword());
        preparedStatement.setString(3, pl.getName());
        preparedStatement.setString(4, pl.getSurname());
        return preparedStatement;
    }

    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     * [1 - long id]
     */
    public PreparedStatement deletePlayer(Connection connection, Player pl) throws SQLException {
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
    public PreparedStatement updatePlayer(Connection connection, Player pl) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("""
                UPDATE walletservice."Player" SET 
                password = ?,
                name = ?,
                surname = ?,
                permission_id = ?
                WHERE login = ?;
                """);
        preparedStatement.setString(1, pl.getPassword());
        preparedStatement.setString(2, pl.getName());
        preparedStatement.setString(3, pl.getSurname());
        preparedStatement.setInt(4, pl.getPermissionId());
        preparedStatement.setString(5, pl.getLogin());
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

                SELECT walletservice."Player".*, walletservice."Permission".name 
                FROM walletservice."Player" 
                JOIN walletservice."Permission" ON walletservice."Player".permission_id = walletservice."Permission".id 
                WHERE walletservice."Player".id = ?
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

                SELECT walletservice."Player".*, walletservice."Permission".name AS permission_name 
                FROM walletservice."Player" 
                JOIN walletservice."Permission" ON walletservice."Player".permission_id = walletservice."Permission".id 
                WHERE walletservice."Player".login = ?
                """);
        preparedStatement.setString(1, pLogin);
        return preparedStatement;
    }

}
