package com.wallet.service.wallet;

import com.wallet.dao.user.UserDao;
import com.wallet.dao.user.UserDaoImpl;
import com.wallet.dao.transaction.TransactionDao;
import com.wallet.dao.wallet.WalletDao;
import com.wallet.domain.entities.User;
import com.wallet.domain.entities.Transaction;
import com.wallet.domain.entities.Wallet;
import com.wallet.utility.exceptions.UserIsNotExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletDao walletDao;
    private final TransactionDao transactionDao;
    private final UserDao userDao;

    @Override
    public void depositMoney(Wallet wallet, BigDecimal amount) throws UserIsNotExistsException {

        Transaction transaction = Transaction.builder()
                .walletId(wallet.getId())
                .playerId(wallet.getPlayerId())
                .id(UUID.randomUUID().toString())
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
    public void withdrawMoney(Wallet wallet, BigDecimal amount) {

        Transaction transaction = Transaction.builder()
                .walletId(wallet.getId())
                .playerId(wallet.getPlayerId())
                .id(UUID.randomUUID().toString())
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
    public BigDecimal checkMoneyAmount(Wallet wallet) {
        return wallet.getMoneyAmount();
    }

    @Override
    public HashMap<String, String> getUserInfo(Wallet wallet) throws UserIsNotExistsException {
        User user = userDao.findPlayer(wallet.getPlayerId()).orElseThrow(UserIsNotExistsException::new);
        System.out.println("login is: " + user.getLogin());
        HashMap<String, String> userInfo = new HashMap<>();
        userInfo.put("surname", user.getSurname());
        userInfo.put("name", user.getName());
        userInfo.put("moneyAmount", (wallet.getMoneyAmount()).toString());
        userInfo.put("walletId", Long.valueOf(wallet.getId()).toString());
        return userInfo;
    }

    @Override
    public ArrayList<Transaction> getTransactionHistory(Wallet wallet) {
        return transactionDao.getTransactionsOfWallet(wallet);
    }

    @Override
    public void processTransactionFromRawData(Wallet wallet, BigDecimal moneyAmount, Transaction.Type type) throws UserIsNotExistsException {
        if (type.equals(Transaction.Type.Deposit)) { depositMoney(wallet, moneyAmount); }
        if (type.equals(Transaction.Type.Withdrawing)) { withdrawMoney(wallet, moneyAmount); }
    }

    @Override
    public Optional<Wallet> getWalletById(long walletId, String login) throws UserIsNotExistsException {
        Wallet wallet = walletDao.getWalletById(walletId);
        User user = userDao.findPlayer(login).orElseThrow(UserIsNotExistsException::new);
        if (wallet.getPlayerId() == user.getId()) {
            return Optional.of(wallet);
        }
        return Optional.empty();
    }
}




