package com.wallet.dao.wallet;

import com.wallet.domain.entities.Player;
import com.wallet.domain.entities.Wallet;
import com.wallet.infrastructure.db.SetupConnection;
import com.wallet.infrastructure.db.statements.PreparedStatementWallet;
import com.wallet.utility.exceptions.PlayerIsNotExistsException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WalletDaoImpl implements WalletDao{

    PreparedStatementWallet preparedStatementWallet;

    public WalletDaoImpl() {
        preparedStatementWallet = PreparedStatementWallet.getInstance();
    }

    @Override
    public void saveWallet(Wallet wallet) {
            try (
                    Connection connection = SetupConnection.getConnection();
                    PreparedStatement preparedStatement = preparedStatementWallet.insertWallet(connection, wallet);
                    )
            {
                preparedStatement.executeUpdate();
            } catch (SQLException e) {}
    }

    @Override
    public void updateWallet(Wallet wallet) {
        try (
                Connection connection = SetupConnection.getConnection();
                PreparedStatement preparedStatement = preparedStatementWallet.updateWallet(connection, wallet);
        )
        {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {}
    }

    @Override
    public void deleteWallet(Wallet wallet) {
        try (
                Connection connection = SetupConnection.getConnection();
                PreparedStatement preparedStatement = preparedStatementWallet.deleteWallet(connection, wallet);
        )
        {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {}
    }

    @Override
    public Wallet getWalletById(long id) {
        Wallet wallet = null;
        try (
                Connection connection = SetupConnection.getConnection();
                PreparedStatement preparedStatement = preparedStatementWallet.getWalletById(connection, id);
                )
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            wallet = extractWalletFromResultSet(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wallet;
    }

    @Override
    public ArrayList<Wallet> getWalletsOfPlayer(Player pl) {
        ArrayList<Wallet> playerWallets = new ArrayList<>();
        try (
             Connection connection = SetupConnection.getConnection();
             PreparedStatement preparedStatement = preparedStatementWallet.getWalletsOfPlayer(connection, pl);
             )
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                playerWallets.add(extractWalletFromResultSet(resultSet));
            }
        } catch (SQLException e ) {
            e.printStackTrace();
        }
        return playerWallets;
    }

    private Wallet extractWalletFromResultSet(ResultSet resultSet) throws SQLException {
        return Wallet.builder()
                .id(resultSet.getLong("id"))
                .playerId(resultSet.getLong("player_id"))
                .moneyAmount(resultSet.getBigDecimal("money_amount"))
                .build();
    }
}
