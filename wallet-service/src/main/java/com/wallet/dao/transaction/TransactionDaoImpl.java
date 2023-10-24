package com.wallet.dao.transaction;

import com.wallet.domain.entities.Transaction;
import com.wallet.domain.entities.Wallet;
import com.wallet.infrastructure.db.SetupConnection;
import com.wallet.infrastructure.db.statements.PreparedStatementTransaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class TransactionDaoImpl implements TransactionDao {

    PreparedStatementTransaction preparedStatementTransaction;
    public TransactionDaoImpl() {
        preparedStatementTransaction = PreparedStatementTransaction.getInstance();
    }
    @Override
    public void saveTransaction(Transaction transaction) {
            try (
                    Connection connection = SetupConnection.getConnection();
                    PreparedStatement preparedStatement = preparedStatementTransaction.insertTransaction(connection, transaction);
            ) {
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void updateTransaction(Transaction transaction) {
        try (
                Connection connection = SetupConnection.getConnection();
                PreparedStatement preparedStatement = preparedStatementTransaction.updateTransaction(connection, transaction);
        ) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTransaction(Transaction transaction) {
        try (
                Connection connection = SetupConnection.getConnection();
                PreparedStatement preparedStatement = preparedStatementTransaction.deleteTransaction(connection, transaction);
        ) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Transaction getTransactionById(UUID id) {
        Transaction transaction = null;
        try (
                Connection connection = SetupConnection.getConnection();
                PreparedStatement preparedStatement = preparedStatementTransaction.getTransactionById(connection, id);
                )
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            transaction = extractTransactionFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transaction;
    }

    @Override
    public ArrayList<Transaction> getTransactionsOfWallet(Wallet wallet) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        try (
                Connection connection = SetupConnection.getConnection();
                PreparedStatement preparedStatement = preparedStatementTransaction.getTransactionHistoryOfWallet(connection, wallet);
        )
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                transactions.add(extractTransactionFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    private Transaction extractTransactionFromResultSet(ResultSet resultSet) throws SQLException {
        return Transaction.builder()
                .transactionId(UUID.fromString(resultSet.getString("id")))
                .walletId(resultSet.getLong("wallet_id"))
                .transactionType(Transaction.Type.valueOf(resultSet.getString("type")))
                .transactionStatus(Transaction.Status.valueOf(resultSet.getString("status")))
                .transactionSum(resultSet.getBigDecimal("money_amount"))
                .transactionDate(resultSet.getTimestamp("timestamp"))
                .build();
    }


}
