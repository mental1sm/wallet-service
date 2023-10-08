package com.wallet.presentation.player_interface;

import com.wallet.infrastructure.UserSession;
import com.wallet.in.UserInputHandler;
import com.wallet.presentation.Localisation;
import com.wallet.services.walletService.WalletService;

import java.math.BigDecimal;
import java.util.Scanner;

public class InterfaceWithdraw extends Interface implements iInterface {

    private final WalletService walletService;
    private final UserSession userSession;

    InterfaceWithdraw(Scanner scanner, WalletService walletService, UserSession userSession) {
        super(scanner);
        this.userSession = userSession;
        this.walletService = walletService;
    }

    @Override
    public iInterface run() {
        System.out.print(Localisation.WALLET_MENU_WITHDRAW_HEADER_RU);
        BigDecimal userInput = UserInputHandler.inputWithdrawMoney(scanner);
        walletService.withdrawMoney(userSession, userInput);
        System.out.println(Localisation.UTIL_LINER);
        return null;
    }
}
