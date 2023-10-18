package com.wallet.presentation.player_interface;

import com.wallet.dao.player.PlayerDaoImpl;
import com.wallet.dao.wallet.WalletDaoImpl;
import com.wallet.entities.Log;
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
        AccountService accountService = new AccountServiceImpl(new PlayerDaoImpl(), new WalletDaoImpl());
        HashMap<String, String> inputValues = UserAuthInputHandler.authInput(scanner);
        UserSession userSession = accountService.authUser(inputValues.get("login"), inputValues.get("password"));
        if (userSession != null) {
            System.out.print(Localisation.AUTH_FINISH_RU);
            loggerService.log(userSession, "Авторизация", Log.InfoLevels.INFO);
            return new UIWalletMenu(scanner, userSession);
        }
        System.out.print(Localisation.USER_IS_NOT_EXISTS_RU);
        return new UIMenu(scanner);
    }


}
