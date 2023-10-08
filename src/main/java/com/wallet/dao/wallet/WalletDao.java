package com.wallet.dao.wallet;

import com.wallet.entities.Wallet;

import java.math.BigDecimal;

public interface WalletDao {
    void saveWallet(Wallet wallet);
    Wallet loadWallet(String PID);

    String getWalletId(Wallet wallet);
    String getPlayerId(Wallet wallet);
    BigDecimal getMoneyAmount(Wallet wallet);

    void setWalletMoneyAmount(BigDecimal moneyAmount, Wallet wallet);

}
