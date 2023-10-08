package com.wallet.infrastructure;

import com.wallet.entities.Transaction;

import java.util.ArrayList;
import java.util.HashMap;

public class TransactionsInMemoryRepository {
    private static TransactionsInMemoryRepository instance = null;
    private HashMap<String, ArrayList<Transaction>> Transactions;

    private TransactionsInMemoryRepository() {
        this.Transactions = new HashMap<>();
    }

    public static TransactionsInMemoryRepository getInstance() {
        if (instance == null) { instance = new TransactionsInMemoryRepository(); }
        return instance;
    }

    public ArrayList<Transaction> getAllTransactionsByWalletId(String walletId) {
        return this.Transactions.get(walletId);
    }
    public void saveTransaction(String walletId, Transaction transaction) {
        System.out.println("2");
        if (this.getAllTransactionsByWalletId(walletId) == null) {
            ArrayList<Transaction> transactionsList = new ArrayList<>();
            transactionsList.add(transaction);
            this.Transactions.put(walletId, transactionsList);
        }
        else {
            ArrayList transactionList = getAllTransactionsByWalletId(walletId);
            transactionList.add(transaction);
        }

    }


}
