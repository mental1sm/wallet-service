package com.wallet.infrastructure;

import com.wallet.entities.Transaction;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Класс TransactionsInMemoryRepository представляет репозиторий для хранения и управления
 * транзакциями в памяти, используя HashMap.
 */
public class TransactionsInMemoryRepository {
    private static TransactionsInMemoryRepository instance = null;
    private HashMap<String, ArrayList<Transaction>> Transactions;

    /**
     * Приватный конструктор класса TransactionsInMemoryRepository создает экземпляр HashMap для хранения
     * транзакций.
     */
    private TransactionsInMemoryRepository() {
        this.Transactions = new HashMap<>();
    }

    /**
     * Метод getInstance возвращает единственный экземпляр класса TransactionsInMemoryRepository с использованием
     * паттерна Singleton.
     *
     * @return Единственный экземпляр TransactionsInMemoryRepository.
     */
    public static TransactionsInMemoryRepository getInstance() {
        if (instance == null) {
            instance = new TransactionsInMemoryRepository();
        }
        return instance;
    }

    /**
     * Метод getAllTransactionsByWalletId возвращает список транзакций для заданного идентификатора кошелька.
     *
     * @param walletId Идентификатор кошелька, для которого необходимо получить список транзакций.
     * @return Список транзакций, связанных с указанным кошельком, или null, если такие транзакции отсутствуют.
     */
    public ArrayList<Transaction> getAllTransactionsByWalletId(String walletId) {
        return this.Transactions.get(walletId);
    }

    /**
     * Метод saveTransaction сохраняет транзакцию для заданного кошелька в репозитории.
     *
     * @param walletId   Идентификатор кошелька, для которого необходимо сохранить транзакцию.
     * @param transaction Объект Transaction, представляющий сохраняемую транзакцию.
     */
    public void saveTransaction(String walletId, Transaction transaction) {
        System.out.println("2");
        if (this.getAllTransactionsByWalletId(walletId) == null) {
            ArrayList<Transaction> transactionsList = new ArrayList<>();
            transactionsList.add(transaction);
            this.Transactions.put(walletId, transactionsList);
        } else {
            ArrayList<Transaction> transactionList = getAllTransactionsByWalletId(walletId);
            transactionList.add(transaction);
        }
    }
}
