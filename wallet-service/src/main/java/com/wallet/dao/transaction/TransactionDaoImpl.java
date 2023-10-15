package com.wallet.dao.transaction;

import com.wallet.entities.Transaction;
import com.wallet.infrastructure.TransactionsInMemoryRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class TransactionDaoImpl implements TransactionDao {

    TransactionsInMemoryRepository transactionsInMemoryRepository = TransactionsInMemoryRepository.getInstance();

    @Override
    public void saveTransaction(Transaction transaction) {
        this.transactionsInMemoryRepository.saveTransaction(transaction.getWalletId(), transaction);
    };
    @Override
    public ArrayList<Transaction> findTransaction(UUID walletId) {
        return this.transactionsInMemoryRepository.getAllTransactionsByWalletId(walletId);
    }


}
