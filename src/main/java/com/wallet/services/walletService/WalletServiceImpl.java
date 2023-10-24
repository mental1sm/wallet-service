package com.wallet.services.walletService;

import com.wallet.dao.player.PlayerDao;
import com.wallet.dao.player.PlayerDaoImpl;
import com.wallet.dao.transaction.TransactionDao;
import com.wallet.dao.wallet.WalletDao;
import com.wallet.domain.entities.Player;
import com.wallet.domain.entities.Transaction;
import com.wallet.domain.entities.Wallet;
import com.wallet.utility.IdGenerator;
import com.wallet.utility.exceptions.PlayerIsNotExistsException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

public class WalletServiceImpl implements WalletService {

    private final WalletDao walletDao;
    private final TransactionDao transactionDao;
    private final PlayerDao playerDao = new PlayerDaoImpl();

    public WalletServiceImpl(WalletDao walletDao, TransactionDao transactionDao) {
        this.walletDao = walletDao;
        this.transactionDao = transactionDao;
    }

    @Override
    public void depositMoney(long walletId, BigDecimal amount) throws PlayerIsNotExistsException {
        Wallet wallet = walletDao.getWalletById(walletId);

        Transaction transaction = Transaction.builder()
                .walletId(wallet.getId())
                .playerId(wallet.getPlayerId())
                .id(IdGenerator.genId().toString())
                .transactionType(Transaction.Type.Deposit)
                .transactionStatus(Transaction.Status.Pending)
                .transactionSum(amount)
                .transactionDate(new Date())
                .build();

        transactionDao.saveTransaction(transaction);

        BigDecimal currentMoney = wallet.getMoneyAmount();
        wallet.setMoneyAmount(currentMoney.add(amount));

        transaction.setTransactionStatus(Transaction.Status.Approved);
        walletDao.updateWallet(wallet);
        transactionDao.updateTransaction(transaction);
    }
    @Override
    public void withdrawMoney(long walletId, BigDecimal amount) throws PlayerIsNotExistsException {
        Wallet wallet = walletDao.getWalletById(walletId);

        Transaction transaction = Transaction.builder()
                .walletId(wallet.getId())
                .playerId(wallet.getPlayerId())
                .id(IdGenerator.genId().toString())
                .transactionType(Transaction.Type.Withdrawing)
                .transactionStatus(Transaction.Status.Pending)
                .transactionSum(amount)
                .transactionDate(new Date())
                .build();
        transactionDao.saveTransaction(transaction);

        BigDecimal currentMoney = wallet.getMoneyAmount();
        if (currentMoney.compareTo(amount) >= 0 ) {
            wallet.setMoneyAmount(currentMoney.subtract(amount));
            transaction.setTransactionStatus(Transaction.Status.Approved);
            transactionDao.updateTransaction(transaction);
            walletDao.updateWallet(wallet);
        }
        else {
            transaction.setTransactionStatus(Transaction.Status.Disapproved);
            transactionDao.updateTransaction(transaction);
        }
    }

    @Override
    public BigDecimal checkMoneyAmount(long walletId) throws PlayerIsNotExistsException {
        Wallet wallet = walletDao.getWalletById(walletId);

        return wallet.getMoneyAmount();
    }

    @Override
    public HashMap<String, String> getUserInfo(String login, long walletId) throws PlayerIsNotExistsException {
        System.out.println("login is: " + login);
        HashMap<String, String> userInfo = new HashMap<>();

        Player pl = playerDao.findPlayer(login).orElseThrow();
        userInfo.put("surname", pl.getSurname());
        userInfo.put("name", pl.getName());

        Wallet wallet = walletDao.getWalletById(walletId);
        userInfo.put("moneyAmount", (wallet.getMoneyAmount()).toString());
        userInfo.put("walletId", Long.valueOf(wallet.getId()).toString());
        return userInfo;
    }

    @Override
    public ArrayList<Transaction> getTransactionHistory(long walletId) throws PlayerIsNotExistsException {
        Wallet wallet = walletDao.getWalletById(walletId);
        return transactionDao.getTransactionsOfWallet(wallet);
    }

    @Override
    public void processTransactionFromRawData(long walletId, BigDecimal moneyAmount, Transaction.Type type) throws PlayerIsNotExistsException {
        if (type.equals(Transaction.Type.Deposit)) { depositMoney(walletId, moneyAmount); }
        if (type.equals(Transaction.Type.Withdrawing)) { withdrawMoney(walletId, moneyAmount); }
    }
}




