package com.wallet.presentation.player_interface;

import com.wallet.in.UserInputHandler;
import com.wallet.presentation.Localisation;

import java.util.Scanner;

public class InterfaceMenu extends Interface implements iInterface {

    public InterfaceMenu(Scanner scanner) {
        super(scanner);
    }

    @Override
    public iInterface run() {
        System.out.print(Localisation.START_MENU_RU);

        String userInput = UserInputHandler.menuNavigation(new String[] {"1", "2", "3"}, scanner);

        switch (userInput) {
            case "1": {
                return new InterfaceAuth(this.scanner);
            }
            case "2": { return new InterfaceReg(this.scanner); }
            case "3": { System.exit(-1); break;}
        }
        return null;
    }
}
