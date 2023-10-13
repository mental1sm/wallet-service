package com.wallet.presentation.player_interface;

import com.wallet.infrastructure.UserSession;
import com.wallet.in.UserDecimalInputHandler;
import com.wallet.presentation.Localisation;
import com.wallet.services.walletService.WalletService;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * Интерфейс для снятия средств со счета
 */
public class UIWithdraw extends AbstractUI implements UI {

    private final WalletService walletService;
    private final UserSession userSession;

    /**
     * Интерфейс для снятия средств со счета
     */
    UIWithdraw(Scanner scanner, WalletService walletService, UserSession userSession) {
        super(scanner);
        this.userSession = userSession;
        this.walletService = walletService;
    }

    @Override
    public UI run() {
        System.out.print(Localisation.WALLET_MENU_WITHDRAW_HEADER_RU);
        BigDecimal userInput = UserDecimalInputHandler.inputWithdrawMoney(scanner);
        walletService.withdrawMoney(userSession, userInput);
        System.out.println(Localisation.UTIL_LINER);
        loggerService.log(userSession, String.format("Попытка снятия на %s", userInput));
        return null;
    }
}