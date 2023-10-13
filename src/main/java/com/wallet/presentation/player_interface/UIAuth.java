package com.wallet.presentation.player_interface;

import com.wallet.in.UserAuthInputHandler;
import com.wallet.infrastructure.UserSession;
import com.wallet.presentation.Localisation;
import com.wallet.services.accountService.AccountService;
import com.wallet.services.accountService.AccountServiceImpl;
import com.wallet.utility.exceptions.PlayerIsNotExistsException;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Интерфейс авторизации
 */
public class UIAuth extends AbstractUI implements UI {

    /**
     * Интерфейс авторизации
     */
    UIAuth(Scanner scanner) {
        super(scanner);
    }

    @Override
    public UI run() {
        AccountService accountService = new AccountServiceImpl();

        HashMap<String, String> inputValues = UserAuthInputHandler.authInput(scanner);

        try {
            UserSession userSession = accountService.authUser(inputValues.get("login"), inputValues.get("password"));
            System.out.print(Localisation.AUTH_FINISH_RU);
            loggerService.log(userSession, "Авторизация");
            return new UIWalletMenu(scanner, userSession);
        } catch (PlayerIsNotExistsException e) {
            System.out.println(Localisation.USER_IS_NOT_EXISTS_RU);
        }

        return new UIMenu(scanner);
    }


}