package com.wallet.presentation.player_interface;


import com.wallet.infrastructure.UserSession;
import com.wallet.in.UserInputHandler;
import com.wallet.presentation.Localisation;
import com.wallet.services.accountService.AccountService;
import com.wallet.services.accountService.AccountServiceImpl;

import java.util.HashMap;
import java.util.Scanner;

public class InterfaceReg extends Interface implements iInterface{

    InterfaceReg(Scanner scanner) {
        super(scanner);
    }

    @Override
    public iInterface run() {
        AccountService accountService = new AccountServiceImpl();

        System.out.print(Localisation.REG_MENU_RU);
        HashMap<String, String> inputValues = UserInputHandler.regInput(scanner);
        UserSession userSession = accountService.regUser(inputValues.get("name"), inputValues.get("surname"), inputValues.get("login"), inputValues.get("password"));
        System.out.print(Localisation.REG_FINISH_RU);

        return new InterfaceWalletMenu(scanner, userSession);
    }
}
