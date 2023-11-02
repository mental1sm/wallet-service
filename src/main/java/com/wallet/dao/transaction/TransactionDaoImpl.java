package com.wallet.dao.transaction;

import com.wallet.domain.entities.Transaction;
import com.wallet.domain.entities.Wallet;
import com.wallet.infrastructure.db.SetupConnection;
import com.wallet.infrastructure.db.statements.PreparedStatementTransaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;


@Repository
@RequiredArgsConstructor
@Slf4j
public class TransactionDaoImpl implements TransactionDao {

    PreparedStatementTransaction preparedStatementTransaction;
    private final SetupConnection setupConnection;

    @Override
    public void saveTransaction(Transaction transaction) {
            try (
                    Connection connection = setupConnection.getConnection();
                    PreparedStatement preparedStatement = preparedStatementTransaction.insertTransaction(connection, transaction);
            ) {
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                log.warn(e.getMessage());
            }
    }

    @Override
    public void updateTransaction(Transaction transaction) {
        try (
                Connection connection = setupConnection.getConnection();
                PreparedStatement preparedStatement = preparedStatementTransaction.updateTransaction(connection, transaction);
        ) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.warn(e.getMessage());
        }
    }

    @Override
    public void deleteTransaction(Transaction transaction) {
        try (
                Connection connection = setupConnection.getConnection();
                PreparedStatement preparedStatement = preparedStatementTransaction.deleteTransaction(connection, transaction);
        ) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.warn(e.getMessage());
        }
    }


    @Override
    public Transaction getTransactionById(UUID id) {
        Transaction transaction = null;
        try (
                Connection connection = setupConnection.getConnection();
                PreparedStatement preparedStatement = preparedStatementTransaction.getTransactionById(connection, id);
                )
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            transaction = extractTransactionFromResultSet(resultSet);
        } catch (SQLException e) {
            log.warn(e.getMessage());
        }

        return transaction;
    }

    @Override
    public ArrayList<Transaction> getTransactionsOfWallet(Wallet wallet) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        try (
                Connection connection = setupConnection.getConnection();
                PreparedStatement preparedStatement = preparedStatementTransaction.getTransactionHistoryOfWallet(connection, wallet);
        )
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                transactions.add(extractTransactionFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            log.warn(e.getMessage());
        }

        return transactions;
    }

    private Transaction extractTransactionFromResultSet(ResultSet resultSet) throws SQLException {
        return Transaction.builder()
                .id(resultSet.getString("id"))
                .walletId(resultSet.getLong("wallet_id"))
                .transactionType(Transaction.Type.valueOf(resultSet.getString("type")))
                .transactionStatus(Transaction.Status.valueOf(resultSet.getString("status")))
                .transactionSum(resultSet.getBigDecimal("money_amount"))
                .transactionDate(resultSet.getTimestamp("timestamp"))
                .build();
    }
}
