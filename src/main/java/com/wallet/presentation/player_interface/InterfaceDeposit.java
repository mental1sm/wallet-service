package com.wallet.presentation.player_interface;

import com.wallet.infrastructure.UserSession;
import com.wallet.in.UserInputHandler;
import com.wallet.presentation.Localisation;
import com.wallet.services.walletService.WalletService;

import java.math.BigDecimal;
import java.util.Scanner;

public class InterfaceDeposit extends Interface implements iInterface{

    private final WalletService walletService;
    private final UserSession userSession;

    InterfaceDeposit(Scanner scanner, WalletService walletService, UserSession userSession) {
        super(scanner);
        this.userSession = userSession;
        this.walletService = walletService;
    }

    @Override
    public iInterface run() {
        System.out.print(Localisation.WALLET_MENU_DEPOSIT_HEADER_RU);
        BigDecimal userInput = UserInputHandler.inputDepositMoney(scanner);
        walletService.depositMoney(userSession, userInput);
        System.out.println(Localisation.UTIL_LINER);
        return null;
    }
}
