package com.wallet.presentation.player_interface;

import com.wallet.in.UserDecimalInputHandler;
import com.wallet.in.UserMenuNavigationHandler;
import com.wallet.presentation.Localisation;

import java.util.Scanner;

/**
 * Интерфейс главного меню
 */
public class InterfaceMenu extends Interface implements iInterface {

    /**
     * Интерфейс главного меню
     */
    public InterfaceMenu(Scanner scanner) {
        super(scanner);
    }

    @Override
    public iInterface run() {
        System.out.print(Localisation.START_MENU_RU);

        String userInput = UserMenuNavigationHandler.menuNavigation(new String[] {"1", "2", "3", "4"}, scanner);

        switch (userInput) {
            case "1" -> {
                return new InterfaceAuth(this.scanner);
            }
            case "2" -> {
                return new InterfaceReg(this.scanner);
            }
            case "3" -> {
                System.exit(-1);
            }
            case "4" -> {
                return new InterfaceAudit(this.scanner);
            }
        }
        return null;
    }
}