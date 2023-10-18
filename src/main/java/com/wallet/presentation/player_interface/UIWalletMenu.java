package com.wallet.presentation.player_interface;


import com.wallet.dao.transaction.TransactionDaoImpl;
import com.wallet.dao.wallet.WalletDaoImpl;
import com.wallet.entities.Log;
import com.wallet.in.UserMenuNavigationHandler;
import com.wallet.infrastructure.UserSession;
import com.wallet.presentation.Localisation;
import com.wallet.services.accountService.AccountService;
import com.wallet.services.walletService.WalletService;
import com.wallet.services.walletService.WalletServiceImpl;
import com.wallet.utility.exceptions.PlayerIsNotExistsException;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Интерфейс меню управления кошельком (счетом)
 */
public class UIWalletMenu extends AbstractUI implements UI {

    private final UserSession userSession;
    AccountService accountService;

    /**
     * Интерфейс меню управления кошельком (счетом)
     */
    public UIWalletMenu(Scanner scanner, UserSession userSession) {
        super(scanner);
        this.userSession = userSession;
    }

    @Override
    public UI run() {
        boolean deauthFlag = false;

        while (!deauthFlag) {
            WalletService walletService = new WalletServiceImpl(new WalletDaoImpl(), new TransactionDaoImpl());
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
                    loggerService.log(userSession, "Запущен процесс пополнения счета", Log.InfoLevels.INFO);
                    UI UIDeposit = new UIDeposit(scanner, walletService, userSession);
                    UIDeposit.run();
                }
                // Снятие
                case "2" -> {
                    loggerService.log(userSession, "Запущен процесс снятия денег", Log.InfoLevels.INFO);
                    UI UIWithdraw = new UIWithdraw(scanner, walletService, userSession);
                    UIWithdraw.run();
                }
                // История транзакций
                case "3" -> {
                    loggerService.log(userSession, "Просмотр истории транзакций", Log.InfoLevels.INFO);
                    UI UITransactionHistory = new UITransactionHistory(scanner, walletService, userSession);
                    UITransactionHistory.run();
                }
                // Выйти из аккаунта
                case "4" -> {
                    deauthFlag = true;
                    loggerService.log(userSession, "Выход из аккаунта", Log.InfoLevels.INFO);
                }
            }
        }
        return new UIMenu(scanner);
    }
}
