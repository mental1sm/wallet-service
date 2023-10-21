package com.wallet.services.walletService;

import com.wallet.dao.player.PlayerDao;
import com.wallet.dao.player.PlayerDaoImpl;
import com.wallet.dao.transaction.TransactionDao;
import com.wallet.dao.wallet.WalletDao;
import com.wallet.domain.entities.Player;
import com.wallet.domain.entities.Transaction;
import com.wallet.domain.entities.Wallet;
import com.wallet.infrastructure.UserSession;
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
    public void depositMoney(UserSession session, BigDecimal amount) throws PlayerIsNotExistsException {
        Optional<Player> optionalPlayer = playerDao.findPlayer(session);
        if (optionalPlayer.isEmpty()) {return;}
        Player player = optionalPlayer.get();
        Wallet wallet = walletDao.getWalletsOfPlayer(player).get(session.getCurrentWalletNum());

        Transaction transaction = Transaction.builder()
                .walletId(wallet.getId())
                .playerId(wallet.getPlayerId())
                .transactionId(IdGenerator.genId())
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
    public void withdrawMoney(UserSession session, BigDecimal amount) throws PlayerIsNotExistsException {
        Optional<Player> optionalPlayer = playerDao.findPlayer(session);
        if (optionalPlayer.isEmpty()) { return; }
        Player player = optionalPlayer.get();
        Wallet wallet = walletDao.getWalletsOfPlayer(player).get(session.getCurrentWalletNum());

        Transaction transaction = Transaction.builder()
                .walletId(wallet.getId())
                .playerId(wallet.getPlayerId())
                .transactionId(IdGenerator.genId())
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
    public BigDecimal checkMoneyAmount(UserSession session) throws PlayerIsNotExistsException {
        Optional<Player> optionalPlayer = playerDao.findPlayer(session);
        Player player = optionalPlayer.orElseThrow();
        Wallet wallet = walletDao.getWalletsOfPlayer(player).get(session.getCurrentWalletNum());

        return wallet.getMoneyAmount();
    }

    @Override
    public HashMap<String, String> getUserInfo(UserSession session) throws PlayerIsNotExistsException {
        Player pl = playerDao.findPlayer(session).orElseThrow();
        HashMap<String, String> userInfo = new HashMap<>();
        userInfo.put("name", pl.getName());
        userInfo.put("surname", pl.getSurname());
        Wallet wallet = walletDao.getWalletsOfPlayer(pl).get(session.getCurrentWalletNum());
        userInfo.put("walletId", Long.valueOf(wallet.getId()).toString());
        return userInfo;
    }

    @Override
    public ArrayList<Transaction> getTransactionHistory(UserSession session) throws PlayerIsNotExistsException {
        Player pl = playerDao.findPlayer(session).orElseThrow();
        Wallet wallet = walletDao.getWalletsOfPlayer(pl).get(session.getCurrentWalletNum());
        return transactionDao.getTransactionsOfWallet(wallet);
    }
}




