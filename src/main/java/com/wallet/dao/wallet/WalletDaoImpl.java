package com.wallet.dao.wallet;

import com.wallet.entities.Player;
import com.wallet.entities.Wallet;
import com.wallet.infrastructure.db.SetupConnection;
import com.wallet.infrastructure.db.statements.PreparedStatementWallet;

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
        SetupConnection.withConnection(connection -> {
            PreparedStatement preparedStatement = preparedStatementWallet.insertWallet(connection);
            preparedStatement.setLong(1, wallet.getPlayerId());
            preparedStatement.setBigDecimal(2, wallet.getWalletMoneyAmount());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        });
    }

    @Override
    public void updateWallet(Wallet wallet) {
        SetupConnection.withConnection(connection -> {
            PreparedStatement preparedStatement = preparedStatementWallet.updateWallet(connection);
            preparedStatement.setBigDecimal(1, wallet.getWalletMoneyAmount());
            preparedStatement.setLong(2, wallet.getWalletId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        });
    }

    @Override
    public void deleteWallet(Wallet wallet) {
        SetupConnection.withConnection(connection -> {
            PreparedStatement preparedStatement = preparedStatementWallet.deleteWallet(connection);
            preparedStatement.setLong(1, wallet.getWalletId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        });
    }

    @Override
    public Wallet getWalletById(long id) {
        Wallet wallet = null;
        try (
                Connection connection = SetupConnection.getConnection();
                PreparedStatement preparedStatement = preparedStatementWallet.getWalletById(connection);
                )
        {
            preparedStatement.setLong(1, id);
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
             PreparedStatement preparedStatement = preparedStatementWallet.getWalletsOfPlayer(connection);
             )
        {
            preparedStatement.setLong(1, pl.getPlayerID());
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
                .walletId(resultSet.getLong("id"))
                .playerId(resultSet.getLong("player_id"))
                .walletMoneyAmount(resultSet.getBigDecimal("money_amount"))
                .build();
    }
}
