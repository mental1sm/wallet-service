package com.wallet.dao.player;

import com.wallet.entities.Player;
import com.wallet.infrastructure.UserSession;
import com.wallet.infrastructure.db.SetupConnection;
import com.wallet.infrastructure.db.statements.PreparedStatementPlayer;
import com.wallet.utility.exceptions.PlayerAllreadyExistsException;
import com.wallet.utility.exceptions.PlayerIsNotExistsException;

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
                PreparedStatement preparedStatement = preparedStatementPlayer.insertPlayer(connection);

        ) {
            preparedStatement.setString(1, pl.getPLogin());
            preparedStatement.setString(2, pl.getPPassword());
            preparedStatement.setString(3, pl.getName());
            preparedStatement.setString(4, pl.getSurname());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PlayerAllreadyExistsException("");
        }
    }

    @Override
    public void updatePlayer(Player pl) {
        SetupConnection.withConnection(connection -> {
            PreparedStatement preparedStatement = preparedStatementPlayer.updatePlayer(connection);
            preparedStatement.setString(1, pl.getPPassword());
            preparedStatement.setString(2, pl.getName());
            preparedStatement.setString(3, pl.getSurname());
            preparedStatement.setInt(4, pl.getPermissionId());
            preparedStatement.setString(5, pl.getPLogin());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        });
    }

    @Override
    public void deletePlayer(Player pl) {
        SetupConnection.withConnection(connection -> {
            PreparedStatement preparedStatement = preparedStatementPlayer.deletePlayer(connection);
            preparedStatement.setString(1, pl.getPLogin());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        });
    }

    @Override
    public Player findPlayer(String pLogin) {
        Player pl = null;
        try (
                Connection connection = SetupConnection.getConnection();
                PreparedStatement preparedStatement = preparedStatementPlayer.getPlayerByLogin(connection);
        )
        {
            preparedStatement.setString(1, pLogin);
            ResultSet resultSet = preparedStatement.executeQuery();

            do {
                pl = extractPlayerFromResultSet(resultSet);
            } while (resultSet.next());
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return pl;
    }

    @Override
    public Player findPlayer(long id) {
        Player pl = null;
        try (
                Connection connection = SetupConnection.getConnection();
                PreparedStatement preparedStatement = preparedStatementPlayer.getPlayerById(connection);
        ) {
            preparedStatement.setLong(1, id);
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
                    .playerID(resultSet.getLong("id"))
                    .permission(Player.Permission.valueOf(resultSet.getString(7)))
                    .permissionId(resultSet.getInt("permission_id"))
                    .name(resultSet.getString("name"))
                    .surname(resultSet.getString("surname"))
                    .pLogin(resultSet.getString("login"))
                    .pPassword(resultSet.getString("password"))
                    .build();
        } else {
            pl = null;
        }
        return pl;
    }

}
