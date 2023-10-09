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
    public Wallet loadWallet(String PID) {
        return walletInMemoryRepository.getWalletById(PID);
    }

    @Override
    public String getWalletId(Wallet wallet) {
        return wallet.getWalletId();
    }
    @Override
    public String getPlayerId(Wallet wallet) {
        return wallet.getPlayerId();
    }
    @Override
    public BigDecimal getMoneyAmount(Wallet wallet) {
        return wallet.getWalletMoneyAmount();
    }

    @Override
    public void setWalletMoneyAmount(BigDecimal moneyAmount, Wallet wallet) {
        wallet.setWalletMoneyAmount(moneyAmount);
    }

}
