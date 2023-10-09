package com.wallet.dao.wallet;

import com.wallet.entities.Wallet;

import java.math.BigDecimal;

/**
 * Интерфейс, предоставляющий методы для управления данными о кошельках.
 */
public interface WalletDao {

    /**
     * Сохраняет информацию о кошельке в хранилище данных.
     *
     * @param wallet Объект кошелька, который необходимо сохранить.
     */
    void saveWallet(Wallet wallet);

    /**
     * Загружает информацию о кошельке по идентификатору игрока (PID).
     *
     * @param PID Идентификатор игрока, для которого нужно загрузить кошелек.
     * @return Объект кошелька, связанный с указанным игроком, или null, если кошелек не найден.
     */
    Wallet loadWallet(String PID);

    /**
     * Получает идентификатор кошелька.
     *
     * @param wallet Объект кошелька.
     * @return Идентификатор кошелька.
     */
    String getWalletId(Wallet wallet);

    /**
     * Получает идентификатор игрока (PID), связанный с указанным кошельком.
     *
     * @param wallet Объект кошелька.
     * @return Идентификатор игрока (PID), связанный с кошельком.
     */
    String getPlayerId(Wallet wallet);

    /**
     * Получает текущий баланс кошелька.
     *
     * @param wallet Объект кошелька.
     * @return Текущий баланс кошелька в виде объекта BigDecimal.
     */
    BigDecimal getMoneyAmount(Wallet wallet);

    /**
     * Устанавливает новый баланс для кошелька.
     *
     * @param moneyAmount Новый баланс кошелька.
     * @param wallet      Объект кошелька, для которого нужно установить новый баланс.
     */
    void setWalletMoneyAmount(BigDecimal moneyAmount, Wallet wallet);
}
