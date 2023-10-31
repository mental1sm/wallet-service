package com.wallet.dao.wallet;

import com.wallet.domain.entities.User;
import com.wallet.domain.entities.Wallet;

import java.util.ArrayList;

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
     * Обновляет информацию о кошельке в хранилище данных.
     *
     * @param wallet Объект кошелька, который необходимо обновить.
     */
    void updateWallet(Wallet wallet);

    /**
     * Метод, удаляющий кошелек из БД
     * @param wallet - Объект кошелька
     */
    void deleteWallet(Wallet wallet);

    /**
     * Метод, возвращающий объект Wallet из БД по его id
     * @param id
     * @return Объект Wallet
     */
    Wallet getWalletById(long id);

    /**
     * Метод, возвращающий коллекцию объектов Wallet из БД
     *
     * @param pl Игрок, чьи кошельки надо вернуть
     * @return Коллекция объектов Wallet
     */
    ArrayList<Wallet> getWalletsOfUser(User pl);

}
