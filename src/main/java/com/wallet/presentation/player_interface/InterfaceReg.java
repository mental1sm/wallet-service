package com.wallet.presentation.player_interface;


import com.wallet.in.UserRegInputHandler;
import com.wallet.infrastructure.UserSession;
import com.wallet.in.UserDecimalInputHandler;
import com.wallet.presentation.Localisation;
import com.wallet.services.accountService.AccountService;
import com.wallet.services.accountService.AccountServiceImpl;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Интерфейс регистрации
 */
public class InterfaceReg extends Interface implements iInterface{

    /**
     * Интерфейс регистрации
     */
    InterfaceReg(Scanner scanner) {
        super(scanner);
    }

    @Override
    public iInterface run() {
        AccountService accountService = new AccountServiceImpl();

        HashMap<String, String> inputValues = UserRegInputHandler.regInput(scanner);
        UserSession userSession = accountService.regUser(inputValues.get("name"), inputValues.get("surname"), inputValues.get("login"), inputValues.get("password"));
        System.out.print(Localisation.REG_FINISH_RU);

        return new InterfaceWalletMenu(scanner, userSession);
    }
}
