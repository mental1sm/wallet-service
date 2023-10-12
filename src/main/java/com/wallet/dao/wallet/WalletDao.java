package com.wallet.dao.wallet;

import com.wallet.entities.Wallet;

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
    Wallet findWallet(String PID);

}
