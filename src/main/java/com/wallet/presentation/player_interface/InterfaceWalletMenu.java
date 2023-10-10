package com.wallet.presentation.player_interface;


import com.wallet.in.UserMenuNavigationHandler;
import com.wallet.infrastructure.UserSession;
import com.wallet.in.UserDecimalInputHandler;
import com.wallet.presentation.Localisation;
import com.wallet.services.accountService.AccountService;
import com.wallet.services.walletService.WalletService;
import com.wallet.services.walletService.WalletServiceImpl;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Интерфейс меню управления кошельком (счетом)
 */
public class InterfaceWalletMenu extends Interface implements iInterface{

    private final UserSession userSession;
    AccountService accountService;

    /**
     * Интерфейс меню управления кошельком (счетом)
     */
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

            String userInput = UserMenuNavigationHandler.menuNavigation(new String[] {"1", "2", "3", "4"}, scanner);

            switch (userInput) {
                // Пополнение
                case "1" -> {
                    loggerService.log(userSession, "Запустить процесс пополнения счета");
                    iInterface interfaceDeposit = new InterfaceDeposit(scanner, walletService, userSession);
                    interfaceDeposit.run();
                }
                // Снятие
                case "2" -> {
                    loggerService.log(userSession, "Запустить процесс снятия денег");
                    iInterface interfaceWithdraw = new InterfaceWithdraw(scanner, walletService, userSession);
                    interfaceWithdraw.run();
                }
                // История транзакций
                case "3" -> {
                    loggerService.log(userSession, "Запустить историю транзакций");
                    iInterface interfaceTransactionHistory = new InterfaceTransactionHistory(scanner, walletService, userSession);
                    interfaceTransactionHistory.run();
                }
                // Выйти из аккаунта
                case "4" -> {
                    deauthFlag = true;
                    loggerService.log(userSession, "Выход из аккаунта");
                }
            }
        }
        return new InterfaceMenu(scanner);
    }
}
