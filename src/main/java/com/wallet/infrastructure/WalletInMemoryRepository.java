package com.wallet.infrastructure;


import com.wallet.entities.Wallet;

import java.util.HashMap;

/**
 * Класс WalletInMemoryRepository представляет репозиторий для хранения и управления кошельками
 * в памяти, используя HashMap.
 */
public class WalletInMemoryRepository {
    private static WalletInMemoryRepository instance = null;
    private final HashMap<String, Wallet> Wallets;

    /**
     * Приватный конструктор класса WalletInMemoryRepository создает экземпляр HashMap для хранения
     * кошельков.
     */
    private WalletInMemoryRepository() {
        this.Wallets = new HashMap<>();
    }

    /**
     * Метод getInstance возвращает единственный экземпляр класса WalletInMemoryRepository с использованием
     * паттерна Singleton.
     *
     * @return Единственный экземпляр WalletInMemoryRepository.
     */
    public static WalletInMemoryRepository getInstance() {
        if (instance == null) {
            instance = new WalletInMemoryRepository();
        }
        return instance;
    }

    /**
     * Метод saveWallet сохраняет кошелек для заданного игрока в репозитории.
     *
     * @param PID    Идентификатор игрока, связанный с кошельком.
     * @param wallet Объект Wallet, представляющий сохраняемый кошелек.
     */
    public void saveWallet(String PID, Wallet wallet) {
        this.Wallets.put(PID, wallet);
    }

    /**
     * Метод getWalletById возвращает кошелек по указанному идентификатору игрока.
     *
     * @param PID Идентификатор игрока, для которого необходимо получить кошелек.
     * @return Объект Wallet, соответствующий указанному идентификатору игрока, или null, если кошелек не найден.
     */
    public Wallet getWalletById(String PID) {
        return this.Wallets.get(PID);
    }
}
