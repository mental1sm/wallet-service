package com.wallet.infrastructure;

import com.wallet.entities.Player;
import com.wallet.entities.Transaction;
import com.wallet.entities.Wallet;
import com.wallet.utility.IdGenerator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class InMemory {
    public static HashMap<String, Player> PLAYERS = new HashMap<>();
    public static HashMap<String, Wallet> WALLETS = new HashMap<>();
    public static HashMap<String, ArrayList> TRANSACTIONS = new HashMap<>();
    public static HashMap<String, ArrayList> LOGS = new HashMap<>();

    public static void newAccount(Player player) {
        PLAYERS.put(player.getPlayerID(), player);
    }

    public static Player getAccountByLoginPassword(String login, String password) {
        Player selectedPlayer = PLAYERS.get(login);
        if (Objects.equals(selectedPlayer.getPPassword(), password)) {
            return selectedPlayer;
        }
        else {
            return null;
        }
    }

    public static Player getAccountByLogin(String login) {
        return PLAYERS.get(login);
    }

    public static void addToWallet(String pID, Wallet wallet) {
        WALLETS.put(pID, wallet);
    }

    public static Wallet getWalletByPID(String pID) {
        return WALLETS.get(pID);
    }

    public static void addTransaction(String pID, Transaction transaction) {
        if (TRANSACTIONS.get(pID) == null) {
            ArrayList<Transaction> transactionsList = new ArrayList<>();
            transactionsList.add(transaction);
            TRANSACTIONS.put(pID, transactionsList);
        }
        else {
            ArrayList transactionList = TRANSACTIONS.get(pID);
            transactionList.add(transaction);
        }
    }

    public static ArrayList<Transaction> getTransactions(String pID) {
        return TRANSACTIONS.get(pID);
    }



}
