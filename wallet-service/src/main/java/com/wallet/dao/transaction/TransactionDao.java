package com.wallet.dao.transaction;
import com.wallet.domain.entities.Transaction;
import com.wallet.domain.entities.Wallet;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Интерфейс, представляющий доступ к данным транзакций.
 */
public interface TransactionDao {

    /**
     * Сохраняет информацию о транзакции в базе данных.
     *
     * @param transaction Объект транзакции, который должен быть сохранен.
     */
    void saveTransaction(Transaction transaction);

    /**
     * Обновляет информацию о транзакции (статус) в БД.
     *
     * @param transaction Объект транзакции, который необходимо обновить.
     */
    void updateTransaction(Transaction transaction);

    /**
     * Удаляет информацию о транзакции (статус) из БД.
     *
     * @param transaction Объект транзакции, который необходимо удалить.
     */
    void deleteTransaction(Transaction transaction);

    /**
     * Загружает список транзакций для указанного id кошелька
     *
     * @param id Идентификатор транзакции.
     * @return Объект транзакции.
     */
    Transaction getTransactionById(UUID id);
    /**
     * Загружает список транзакций для указанного id кошелька
     *
     * @param wallet Объект кошелька, для которого нужно загрузить транзакции.
     * @return Список объектов транзакций для указанного кошелька.
     */
    ArrayList<Transaction> getTransactionsOfWallet(Wallet wallet);

}
