package com.wallet.presentation.player_interface;

import com.wallet.entities.Transaction;
import com.wallet.infrastructure.UserSession;
import com.wallet.presentation.Localisation;
import com.wallet.services.walletService.WalletService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class InterfaceTransactionHistory extends Interface implements iInterface {

    WalletService walletService;
    UserSession userSession;


    public InterfaceTransactionHistory(Scanner scanner, WalletService walletService, UserSession userSession) {
        super(scanner);
        this.walletService = walletService;
        this.userSession = userSession;
    }

    @Override
    public iInterface run() {

        System.out.print(Localisation.WALLET_MENU_TRANSACTION_HISTORY_HEADER_RU);
        ArrayList<Transaction> transactionHistory = walletService.getTransactionHistory(userSession);
        try {


            for (Transaction transaction : transactionHistory) {
                System.out.printf(
                        Localisation.WALLET_MENU_TRANSACTION_DETAILS_RU,
                        transaction.getTransactionId(),
                        Localisation.transactionTypeCodeTranslateRu(transaction.getTransactionType()),
                        Localisation.transactionStatusCodeTranslateRu(transaction.getTransactionStatus()),
                        transaction.getTransactionDate(),
                        transaction.getTransactionSum()
                );
            }
        } catch (NullPointerException e) {
            System.out.println(Localisation.EMPTY_TRANSACTION_HISTORY_RU);
        }
        System.out.println(Localisation.UTIL_LINER);
        System.out.println(Localisation.WALLET_MENU_TRANSACTION_GOBACK_RU);
        scanner.next();
        return null;
    }

}
