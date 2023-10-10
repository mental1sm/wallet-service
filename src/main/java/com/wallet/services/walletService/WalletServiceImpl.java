package com.wallet.services.walletService;

import com.wallet.dao.player.PlayerDao;
import com.wallet.dao.player.PlayerDaoImpl;
import com.wallet.dao.transaction.TransactionDao;
import com.wallet.dao.transaction.TransactionDaoImpl;
import com.wallet.dao.wallet.WalletDao;
import com.wallet.dao.wallet.WalletDaoImpl;
import com.wallet.entities.Player;
import com.wallet.entities.Transaction;
import com.wallet.entities.Wallet;
import com.wallet.infrastructure.UserSession;
import com.wallet.utility.IdGenerator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class WalletServiceImpl implements WalletService {

    private final WalletDao walletDao;
    private final TransactionDao transactionDao;
    private final PlayerDao playerDao = new PlayerDaoImpl();

    public WalletServiceImpl() {
        walletDao = new WalletDaoImpl();
        transactionDao = new TransactionDaoImpl();
    }

    @Override
    public void depositMoney(UserSession session, BigDecimal amount) {
        Wallet wallet = walletDao.loadWallet(session.getPlayerID());

        Transaction transaction = Transaction.builder()
                .walletId(wallet.getWalletId())
                .playerId(wallet.getPlayerId())
                .transactionId(IdGenerator.genId())
                .transactionType(1)
                .transactionStatus(0)
                .transactionSum(amount)
                .transactionDate(new Date())
                .build();

        BigDecimal currentMoney = wallet.getWalletMoneyAmount();
        wallet.setWalletMoneyAmount(currentMoney.add(amount));

        transactionDao.setTransactionStatus(1, transaction);
        transactionDao.saveTransaction(transaction);
    }
    @Override
    public void withdrawMoney(UserSession session, BigDecimal amount) {
        Wallet wallet = this.walletDao.loadWallet(session.getPlayerID());

        Transaction transaction = Transaction.builder()
                .walletId(wallet.getWalletId())
                .playerId(wallet.getPlayerId())
                .transactionId(IdGenerator.genId())
                .transactionType(2)
                .transactionStatus(0)
                .transactionSum(amount)
                .transactionDate(new Date())
                .build();

        BigDecimal currentMoney = wallet.getWalletMoneyAmount();

        if (currentMoney.compareTo(amount) >= 0 ) {
            walletDao.setWalletMoneyAmount(currentMoney.subtract(amount), wallet);
            transactionDao.setTransactionStatus(1, transaction);
        }
        else {
            transactionDao.setTransactionStatus(2, transaction);
        }
        transactionDao.saveTransaction(transaction);
    }

    @Override
    public BigDecimal checkMoneyAmount(UserSession session) {
        Wallet wallet = this.walletDao.loadWallet(session.getPlayerID());

        return walletDao.getMoneyAmount(wallet);
    }

    @Override
    public HashMap<String, String> getUserInfo(UserSession session) {
        Player pl = playerDao.loadPlayer(session);
        HashMap<String, String> userInfo = new HashMap<>();
        userInfo.put("name", playerDao.getName(pl));
        userInfo.put("surname", playerDao.getSurname(pl));
        Wallet wallet = walletDao.loadWallet(playerDao.getPID(pl));
        userInfo.put("walletId", walletDao.getWalletId(wallet));
        return userInfo;
    }

    @Override
    public ArrayList<Transaction> getTransactionHistory(UserSession session) {
        Wallet wallet = this.walletDao.loadWallet(session.getPlayerID());
        return transactionDao.loadTransactions(walletDao.getWalletId(wallet));

    }
}




