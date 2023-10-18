package com.wallet.infrastructure.db.statements;

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
    public PreparedStatement insertPlayer(Connection connection) throws SQLException {
        return connection.prepareStatement("INSERT INTO walletservice.\"Player\" VALUES (DEFAULT, ?, ?, ?, ?, 3);");
    }

    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     * [1 - long id]
     */
    public PreparedStatement deletePlayer(Connection connection) throws SQLException {
        return connection.prepareStatement("DELETE FROM walletservice.\"Player\" WHERE login = ?;");
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
    public PreparedStatement updatePlayer(Connection connection) throws SQLException {
        return connection.prepareStatement("""
                UPDATE walletservice."Player" SET 
                password = ?,
                name = ?,
                surname = ?,
                permission_id = ?
                WHERE login = ?;
                """);
    }

    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     * [1 - int id]
     */
    public PreparedStatement getPlayerById(Connection connection) throws SQLException {
        return connection.prepareStatement("""

                SELECT walletservice."Player".*, walletservice."Permission".name 
                FROM walletservice."Player" 
                JOIN walletservice."Permission" ON walletservice."Player".permission_id = walletservice."Permission".id 
                WHERE walletservice."Player".id = ?
                """);
    }

    /**
     * Подготовленный Statement
     * @param connection Соединение с БД
     * @return PreparedStatement объект
     * [1 - String login]
     */
    public PreparedStatement getPlayerByLogin(Connection connection) throws SQLException {
        return connection.prepareStatement("""

                SELECT walletservice."Player".*, walletservice."Permission".name AS permission_name 
                FROM walletservice."Player" 
                JOIN walletservice."Permission" ON walletservice."Player".permission_id = walletservice."Permission".id 
                WHERE walletservice."Player".login = ?
                """);
    }

}
