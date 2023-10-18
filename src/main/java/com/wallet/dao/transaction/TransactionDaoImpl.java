package com.wallet.dao.transaction;

import com.wallet.entities.Transaction;
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
        SetupConnection.withConnection(connection -> {
            PreparedStatement preparedStatement = preparedStatementTransaction.insertTransaction(connection);
            preparedStatement.setString(1, transaction.getTransactionId().toString());
            preparedStatement.setLong(2, transaction.getWalletId());
            preparedStatement.setString(3, transaction.getTransactionType().toString());
            preparedStatement.setString(4, transaction.getTransactionStatus().toString());
            preparedStatement.setBigDecimal(5, transaction.getTransactionSum());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        });
    }

    @Override
    public void updateTransaction(Transaction transaction) {
        SetupConnection.withConnection(connection -> {
            PreparedStatement preparedStatement = preparedStatementTransaction.updateTransaction(connection);
            preparedStatement.setString(1, transaction.getTransactionStatus().toString());
            preparedStatement.setString(2, transaction.getTransactionId().toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        });
    }

    @Override
    public void deleteTransaction(Transaction transaction) {
        SetupConnection.withConnection(connection -> {
            PreparedStatement preparedStatement = preparedStatementTransaction.deleteTransaction(connection);
            preparedStatement.setString(1, transaction.getTransactionId().toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        });
    }


    @Override
    public Transaction getTransactionById(UUID id) {
        Transaction transaction = null;
        try (
                Connection connection = SetupConnection.getConnection();
                PreparedStatement preparedStatement = preparedStatementTransaction.getTransactionById(connection);
                )
        {
            preparedStatement.setString(1, id.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            transaction = extractTransactionFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transaction;
    }

    @Override
    public ArrayList<Transaction> getTransactionsOfWallet(long walletId) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        try (
                Connection connection = SetupConnection.getConnection();
                PreparedStatement preparedStatement = preparedStatementTransaction.getTransactionHistoryOfWallet(connection);
        )
        {
            preparedStatement.setLong(1, walletId);
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
