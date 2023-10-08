package com.wallet.services.walletService;


import com.wallet.entities.Transaction;
import com.wallet.infrastructure.UserSession;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public interface WalletService {

    void depositMoney(UserSession session, BigDecimal amount);
    void withdrawMoney(UserSession session, BigDecimal amount);

    BigDecimal checkMoneyAmount(UserSession session);
    HashMap<String, String> getUserInfo(UserSession session);
    ArrayList<Transaction> getTransactionHistory(UserSession session);
}
