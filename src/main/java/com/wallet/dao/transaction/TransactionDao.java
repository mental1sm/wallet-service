package com.wallet.dao.transaction;

import com.wallet.entities.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public interface TransactionDao {
    void saveTransaction(Transaction transaction);
    ArrayList<Transaction> loadTransactions(String PID);

    String getPID(Transaction transaction);
    String getTransactionID(Transaction transaction);
    String getWalletId(Transaction transaction);
    int getTransactionType(Transaction transaction);
    int getTransactionStatus(Transaction transaction);
    BigDecimal getTransactionSum(Transaction transaction);
    Date getTransactionDate(Transaction transaction);

    void setTransactionStatus(int statusCode, Transaction transaction);
}
