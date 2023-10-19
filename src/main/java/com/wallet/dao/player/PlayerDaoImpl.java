package com.wallet.dao.player;

import com.wallet.entities.Player;
import com.wallet.infrastructure.UserSession;
import com.wallet.infrastructure.db.SetupConnection;
import com.wallet.infrastructure.db.statements.PreparedStatementPlayer;
import com.wallet.utility.exceptions.PlayerAllreadyExistsException;

import java.sql.*;


public class PlayerDaoImpl implements PlayerDao{
    PreparedStatementPlayer preparedStatementPlayer;

    public PlayerDaoImpl() {
        preparedStatementPlayer = PreparedStatementPlayer.getInstance();
    }

    @Override
    public void savePlayer(Player pl) throws PlayerAllreadyExistsException {
        try (
                Connection connection = SetupConnection.getConnection();
                PreparedStatement preparedStatement = preparedStatementPlayer.insertPlayer(connection, pl)
        ) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PlayerAllreadyExistsException("");
        }
    }

    @Override
    public void updatePlayer(Player pl) {
        SetupConnection.withConnection(connection -> {
            PreparedStatement preparedStatement = preparedStatementPlayer.updatePlayer(connection, pl);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        });
    }

    @Override
    public void deletePlayer(Player pl) {
        SetupConnection.withConnection(connection -> {
            PreparedStatement preparedStatement = preparedStatementPlayer.deletePlayer(connection, pl);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        });
    }

    @Override
    public Player findPlayer(String pLogin) {
        Player pl;
        try (
                Connection connection = SetupConnection.getConnection();
                PreparedStatement preparedStatement = preparedStatementPlayer.getPlayerByLogin(connection, pLogin)
        )
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            pl = extractPlayerFromResultSet(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return pl;
    }

    @Override
    public Player findPlayer(long id) {
        Player pl;
        try (
                Connection connection = SetupConnection.getConnection();
                PreparedStatement preparedStatement = preparedStatementPlayer.getPlayerById(connection, id)
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            pl = extractPlayerFromResultSet(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return pl;
    }

    @Override
    public Player findPlayer(UserSession userSession) {
        if (!(userSession == null)) {
            return findPlayer(userSession.getPlayerID());
        } else {
            return null;
        }
    }

    /**
     * Извлекает из ResultSet данные игрока и возвращает объект
    */
    private Player extractPlayerFromResultSet(ResultSet resultSet) throws SQLException {
        Player pl;

        if (resultSet.next()) {
            pl =  Player.builder()
                    .id(resultSet.getLong("id"))
                    .permissionLevel(Player.Permission.valueOf(resultSet.getString(7)))
                    .permissionId(resultSet.getInt("permission_id"))
                    .name(resultSet.getString("name"))
                    .surname(resultSet.getString("surname"))
                    .login(resultSet.getString("login"))
                    .password(resultSet.getString("password"))
                    .build();
        } else {
            pl = null;
        }
        return pl;
    }

}
