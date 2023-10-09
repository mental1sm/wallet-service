package com.wallet.dao.transaction;

import com.wallet.entities.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

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
    ArrayList<Transaction> loadTransactions(String PID);

    /**
     * Получает идентификатор игрока (PID), связанный с указанной транзакцией.
     *
     * @param transaction Объект транзакции.
     * @return Идентификатор игрока, связанный с транзакцией.
     */
    String getPID(Transaction transaction);

    /**
     * Получает идентификатор транзакции.
     *
     * @param transaction Объект транзакции.
     * @return Идентификатор транзакции.
     */
    String getTransactionID(Transaction transaction);

    /**
     * Получает идентификатор кошелька, связанный с указанной транзакцией.
     *
     * @param transaction Объект транзакции.
     * @return Идентификатор кошелька, связанный с транзакцией.
     */
    String getWalletId(Transaction transaction);

    /**
     * Получает тип транзакции.
     *
     * @param transaction Объект транзакции.
     * @return Тип транзакции.
     *     "Пополнение" = 1;
     *     "Снятие" = 2;
     */
    int getTransactionType(Transaction transaction);

    /**
     * Получает статус транзакции.
     *
     * @param transaction Объект транзакции.
     * @return Статус транзакции.
     *      По умолчанию (при создании) "в обработке" = 0;
     *      "одобрено" = 1;
     *      "отклонено" = 2.
     */
    int getTransactionStatus(Transaction transaction);

    /**
     * Получает сумму транзакции.
     *
     * @param transaction Объект транзакции.
     * @return Сумма транзакции.
     */
    BigDecimal getTransactionSum(Transaction transaction);

    /**
     * Получает дату и время транзакции.
     *
     * @param transaction Объект транзакции.
     * @return Дата и время транзакции.
     */
    Date getTransactionDate(Transaction transaction);

    /**
     * Устанавливает статус транзакции на указанный код.
     *
     * @param statusCode  Код статуса, который нужно установить для транзакции.
     *      По умолчанию (при создании) "в обработке" = 0;
     *      "одобрено" = 1;
     *      "отклонено" = 2.
     * @param transaction Объект транзакции, для которой нужно установить статус.
     */
    void setTransactionStatus(int statusCode, Transaction transaction);
}
