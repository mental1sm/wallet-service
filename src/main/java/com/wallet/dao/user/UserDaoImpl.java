package com.wallet.dao.user;

import com.wallet.domain.entities.User;
import com.wallet.infrastructure.db.SetupConnection;
import com.wallet.infrastructure.db.statements.PreparedStatementPlayer;
import com.wallet.utility.exceptions.UserAllreadyExistsException;
import com.wallet.utility.exceptions.UserIsNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Optional;


@Repository
public class UserDaoImpl implements UserDao {
    PreparedStatementPlayer preparedStatementPlayer;
    private final SetupConnection setupConnection;

    @Autowired
    public UserDaoImpl(SetupConnection setupConnection) {
        preparedStatementPlayer = PreparedStatementPlayer.getInstance();
        this.setupConnection = setupConnection;
    }

    @Override
    public void savePlayer(User pl) throws UserAllreadyExistsException {
        try (
                Connection connection = setupConnection.getConnection();
                PreparedStatement preparedStatement = preparedStatementPlayer.insertPlayer(connection, pl)
        ) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserAllreadyExistsException();
        }
    }

    @Override
    public void updatePlayer(User pl) throws UserIsNotExistsException {
            try (
                    Connection connection = setupConnection.getConnection();
                    PreparedStatement preparedStatement = preparedStatementPlayer.updatePlayer(connection, pl);
                    )
            {
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new UserIsNotExistsException();
            }
    }

    @Override
    public void deletePlayer(User pl) throws UserIsNotExistsException {
        try (
                Connection connection = setupConnection.getConnection();
                PreparedStatement preparedStatement = preparedStatementPlayer.deletePlayer(connection, pl);
        )
        {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserIsNotExistsException();
        }
    }

    @Override
    public Optional<User> findPlayer(String pLogin) {
        User pl;
        try (
                Connection connection = setupConnection.getConnection();
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
    public Optional<User> findPlayer(long id) {
        User pl;
        try (
                Connection connection = setupConnection.getConnection();
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

    /**
     * Извлекает из ResultSet данные игрока и возвращает объект
    */
    private User extractPlayerFromResultSet(ResultSet resultSet) throws SQLException {
        User pl;

        if (resultSet.next()) {
            pl =  User.builder()
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
