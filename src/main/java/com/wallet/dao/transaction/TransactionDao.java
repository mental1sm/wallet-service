package com.wallet.dao.transaction;

import com.wallet.entities.Transaction;

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
     * Загружает список транзакций для указанного идентификатора игрока (PID).
     *
     * @param PID Идентификатор игрока, для которого нужно загрузить транзакции.
     * @return Список объектов транзакций для указанного игрока.
     */
    ArrayList<Transaction> findTransaction(UUID PID);

}
