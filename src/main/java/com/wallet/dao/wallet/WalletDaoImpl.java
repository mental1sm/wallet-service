package com.wallet.dao.wallet;

import com.wallet.entities.Wallet;
import com.wallet.infrastructure.WalletInMemoryRepository;

import java.math.BigDecimal;

public class WalletDaoImpl implements WalletDao{

    WalletInMemoryRepository walletInMemoryRepository = WalletInMemoryRepository.getInstance();

    @Override
    public void saveWallet(Wallet wallet) {
        this.walletInMemoryRepository.saveWallet(wallet.getPlayerId(), wallet);
    }
    @Override
    public Wallet findWallet(String PID) {
        return walletInMemoryRepository.getWalletById(PID);
    }

}
