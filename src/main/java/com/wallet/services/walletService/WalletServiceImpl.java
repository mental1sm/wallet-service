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
        Wallet wallet = walletDao.findWallet(session.getPlayerID());

        Transaction transaction = Transaction.builder()
                .walletId(wallet.getWalletId())
                .playerId(wallet.getPlayerId())
                .transactionId(IdGenerator.genId())
                .transactionType(Transaction.Type.Deposit)
                .transactionStatus(Transaction.Status.Pending)
                .transactionSum(amount)
                .transactionDate(new Date())
                .build();

        BigDecimal currentMoney = wallet.getWalletMoneyAmount();
        wallet.setWalletMoneyAmount(currentMoney.add(amount));

        transaction.setTransactionStatus(Transaction.Status.Approved);
        transactionDao.saveTransaction(transaction);
    }
    @Override
    public void withdrawMoney(UserSession session, BigDecimal amount) {
        Wallet wallet = this.walletDao.findWallet(session.getPlayerID());

        Transaction transaction = Transaction.builder()
                .walletId(wallet.getWalletId())
                .playerId(wallet.getPlayerId())
                .transactionId(IdGenerator.genId())
                .transactionType(Transaction.Type.Withdrawing)
                .transactionStatus(Transaction.Status.Pending)
                .transactionSum(amount)
                .transactionDate(new Date())
                .build();

        BigDecimal currentMoney = wallet.getWalletMoneyAmount();

        if (currentMoney.compareTo(amount) >= 0 ) {
            wallet.setWalletMoneyAmount(currentMoney.subtract(amount));
            transaction.setTransactionStatus(Transaction.Status.Approved);
        }
        else {
            transaction.setTransactionStatus(Transaction.Status.Disapproved);
        }
        transactionDao.saveTransaction(transaction);
    }

    @Override
    public BigDecimal checkMoneyAmount(UserSession session) {
        Wallet wallet = this.walletDao.findWallet(session.getPlayerID());

        return wallet.getWalletMoneyAmount();
    }

    @Override
    public HashMap<String, String> getUserInfo(UserSession session) {
        Player pl = playerDao.findPlayer(session);
        HashMap<String, String> userInfo = new HashMap<>();
        userInfo.put("name", pl.getName());
        userInfo.put("surname", pl.getSurname());
        Wallet wallet = walletDao.findWallet(pl.getPlayerID());
        userInfo.put("walletId", wallet.getWalletId().toString());
        return userInfo;
    }

    @Override
    public ArrayList<Transaction> getTransactionHistory(UserSession session) {
        Wallet wallet = this.walletDao.findWallet(session.getPlayerID());
        return transactionDao.findTransaction(wallet.getWalletId());

    }
}




