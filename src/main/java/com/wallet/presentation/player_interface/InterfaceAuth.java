package com.wallet.presentation.player_interface;

import com.wallet.in.UserAuthInputHandler;
import com.wallet.infrastructure.UserSession;
import com.wallet.in.UserDecimalInputHandler;
import com.wallet.presentation.Localisation;
import com.wallet.services.accountService.AccountService;
import com.wallet.services.accountService.AccountServiceImpl;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Интерфейс авторизации
 */
public class InterfaceAuth extends Interface implements iInterface{

    /**
     * Интерфейс авторизации
     */
    InterfaceAuth(Scanner scanner) {
        super(scanner);
    }

    @Override
    public iInterface run() {
        AccountService accountService = new AccountServiceImpl();

        HashMap<String, String> inputValues = UserAuthInputHandler.authInput(scanner);

        try {
            UserSession userSession = accountService.authUser(inputValues.get("login"), inputValues.get("password"));
            System.out.print(Localisation.AUTH_FINISH_RU);
            loggerService.log(userSession, "Авторизация");
            return new InterfaceWalletMenu(scanner, userSession);
        } catch (NullPointerException e) {
            System.out.println(Localisation.USER_IS_NOT_EXISTS_RU);
        }

        return new InterfaceMenu(scanner);
    }


}
