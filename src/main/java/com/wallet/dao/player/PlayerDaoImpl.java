package com.wallet.dao.player;

import com.wallet.domain.entities.Player;
import com.wallet.infrastructure.UserSession;
import com.wallet.infrastructure.db.SetupConnection;
import com.wallet.infrastructure.db.statements.PreparedStatementPlayer;
import com.wallet.utility.exceptions.PlayerAllreadyExistsException;
import com.wallet.utility.exceptions.PlayerIsNotExistsException;

import java.sql.*;
import java.util.Optional;


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
            throw new PlayerAllreadyExistsException();
        }
    }

    @Override
    public void updatePlayer(Player pl) throws PlayerIsNotExistsException {
            try (
                    Connection connection = SetupConnection.getConnection();
                    PreparedStatement preparedStatement = preparedStatementPlayer.updatePlayer(connection, pl);
                    )
            {
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new PlayerIsNotExistsException();
            }
    }

    @Override
    public void deletePlayer(Player pl) throws PlayerIsNotExistsException {
        try (
                Connection connection = SetupConnection.getConnection();
                PreparedStatement preparedStatement = preparedStatementPlayer.deletePlayer(connection, pl);
        )
        {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PlayerIsNotExistsException();
        }
    }

    @Override
    public Optional<Player> findPlayer(String pLogin) {
        Player pl;
        try (
                Connection connection = SetupConnection.getConnection();
                PreparedStatement preparedStatement = preparedStatementPlayer.getPlayerByLogin(connection, pLogin)
        )
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            pl = extractPlayerFromResultSet(resultSet);
            resultSet.close();
            return Optional.ofNullable(pl);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Player> findPlayer(long id) {
        Player pl;
        try (
                Connection connection = SetupConnection.getConnection();
                PreparedStatement preparedStatement = preparedStatementPlayer.getPlayerById(connection, id)
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            pl = extractPlayerFromResultSet(resultSet);
            resultSet.close();
            return Optional.ofNullable(pl);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Player> findPlayer(UserSession userSession) {
        if (!(userSession == null)) {
            return findPlayer(userSession.getPlayerID());
        } return Optional.empty();
    }

    /**
     * Извлекает из ResultSet данные игрока и возвращает объект
    */
    private Player extractPlayerFromResultSet(ResultSet resultSet) throws SQLException {
        Player pl;

        if (resultSet.next()) {
            pl =  Player.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .surname(resultSet.getString("surname"))
                    .login(resultSet.getString("login"))
                    .password(resultSet.getString("password"))
                    .email(resultSet.getString("email"))
                    .build();
        } else {
            pl = null;
        }
        return pl;
    }

}
