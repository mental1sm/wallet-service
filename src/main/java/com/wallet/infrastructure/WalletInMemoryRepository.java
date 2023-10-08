package com.wallet.infrastructure;


import com.wallet.entities.Wallet;

import java.util.HashMap;

public class WalletInMemoryRepository {
    private static WalletInMemoryRepository instance = null;
    private final HashMap<String, Wallet> Wallets;

    private WalletInMemoryRepository() {
        this.Wallets = new HashMap<>();
    }

    public static WalletInMemoryRepository getInstance() {
        if (instance == null) { instance = new WalletInMemoryRepository(); }
        return instance;
    }

    public void saveWallet(String PID, Wallet wallet) {
        this.Wallets.put(PID, wallet);
    }

    public Wallet getWalletById(String PID) {
        return this.Wallets.get(PID);
    }
}
