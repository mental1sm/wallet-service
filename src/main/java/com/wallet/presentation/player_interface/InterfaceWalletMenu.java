package com.wallet.presentation.player_interface;


import com.wallet.infrastructure.UserSession;
import com.wallet.in.UserInputHandler;
import com.wallet.presentation.Localisation;
import com.wallet.services.accountService.AccountService;
import com.wallet.services.walletService.WalletService;
import com.wallet.services.walletService.WalletServiceImpl;

import java.util.HashMap;
import java.util.Scanner;

public class InterfaceWalletMenu extends Interface implements iInterface{

    private final UserSession userSession;
    AccountService accountService;

    public InterfaceWalletMenu(Scanner scanner, UserSession userSession) {
        super(scanner);
        this.userSession = userSession;
    }

    @Override
    public iInterface run() {
        boolean deauthFlag = false;

        while (!deauthFlag) {
            WalletService walletService = new WalletServiceImpl();
            HashMap<String, String> userInfo = walletService.getUserInfo(userSession);

            System.out.printf(
                    Localisation.WALLET_MENU_INFO_RU,
                    userInfo.get("name") + " " + userInfo.get("surname"),
                    userInfo.get("walletId"),
                    walletService.checkMoneyAmount(userSession).toString()
            );

            String userInput = UserInputHandler.menuNavigation(new String[] {"1", "2", "3", "4"}, scanner);

            switch (userInput) {
                case "1": {
                    iInterface interfaceDeposit = new InterfaceDeposit(scanner, walletService, userSession);
                    interfaceDeposit.run();
                    break;
                }
                case "2": {
                    iInterface interfaceWithdraw = new InterfaceWithdraw(scanner, walletService, userSession);
                    interfaceWithdraw.run();
                    break;
                }
                case "3": {
                    iInterface interfaceTransactionHistory = new InterfaceTransactionHistory(scanner, walletService, userSession);
                    interfaceTransactionHistory.run();
                    break;
                }
                case "4": {
                    deauthFlag = true;
                }
            }
        }
        return new InterfaceMenu(scanner);
    }
}
