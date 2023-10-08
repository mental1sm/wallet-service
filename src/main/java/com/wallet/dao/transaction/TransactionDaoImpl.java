package com.wallet.dao.transaction;

import com.wallet.entities.Transaction;
import com.wallet.infrastructure.TransactionsInMemoryRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class TransactionDaoImpl implements TransactionDao {

    TransactionsInMemoryRepository transactionsInMemoryRepository = TransactionsInMemoryRepository.getInstance();

    @Override
    public void saveTransaction(Transaction transaction) {
        this.transactionsInMemoryRepository.saveTransaction(transaction.getWalletId(), transaction);
    };
    @Override
    public ArrayList<Transaction> loadTransactions(String walletId) {
        return this.transactionsInMemoryRepository.getAllTransactionsByWalletId(walletId);
    }

    @Override
    public String getPID(Transaction transaction) {
        return transaction.getPlayerId();
    }
    @Override
    public String getTransactionID(Transaction transaction) {
        return transaction.getTransactionId();
    }
    @Override
    public String getWalletId(Transaction transaction) {
        return transaction.getWalletId();
    }
    @Override
    public int getTransactionType(Transaction transaction) {
        return transaction.getTransactionType();
    }
    @Override
    public int getTransactionStatus(Transaction transaction) {
        return transaction.getTransactionStatus();
    }
    @Override
    public BigDecimal getTransactionSum(Transaction transaction) {
        return transaction.getTransactionSum();
    }
    @Override
    public Date getTransactionDate(Transaction transaction) {
        return transaction.getTransactionDate();
    }

    @Override
    public void setTransactionStatus(int statusCode, Transaction transaction) {
        transaction.setTransactionStatus(statusCode);
    }

}
