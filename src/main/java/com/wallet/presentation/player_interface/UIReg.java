package com.wallet.presentation.player_interface;


import com.wallet.dao.player.PlayerDaoImpl;
import com.wallet.dao.wallet.WalletDaoImpl;
import com.wallet.entities.Log;
import com.wallet.in.UserRegInputHandler;
import com.wallet.infrastructure.UserSession;
import com.wallet.presentation.Localisation;
import com.wallet.services.accountService.AccountService;
import com.wallet.services.accountService.AccountServiceImpl;
import com.wallet.utility.exceptions.PlayerAllreadyExistsException;
import com.wallet.utility.exceptions.PlayerIsNotExistsException;

import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;

/**
 * Интерфейс регистрации
 */
public class UIReg extends AbstractUI implements UI {

    /**
     * Интерфейс регистрации
     */
    UIReg(Scanner scanner) {
        super(scanner);
    }

    @Override
    public UI run() {
        AccountService accountService = new AccountServiceImpl(new PlayerDaoImpl(), new WalletDaoImpl());
        HashMap<String, String> inputValues = UserRegInputHandler.regInput(scanner);
        Optional<UserSession> userSession = registerUser(accountService, inputValues);
        if (userSession.isPresent()) {
            UserSession session = userSession.get();
            System.out.print(Localisation.REG_FINISH_RU);
            loggerService.log(session, "Регистрация", Log.InfoLevels.INFO);
            return new UIWalletMenu(scanner, session);
        } else {
            System.out.println(Localisation.USER_IS_ALREADY_EXISTS_RU);
            return new UIMenu(scanner);
        }
    }

    private Optional<UserSession> registerUser(AccountService accountService, HashMap<String, String> inputValues) {
        try {
            return accountService.regUser(
                    inputValues.get("name"),
                    inputValues.get("surname"),
                    inputValues.get("login"),
                    inputValues.get("password"));
        } catch (PlayerAllreadyExistsException e) {
            return Optional.empty();
        }
    }
}
