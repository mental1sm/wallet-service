package com.wallet.presentation.player_interface;

import com.wallet.dao.player.PlayerDaoImpl;
import com.wallet.in.UserMenuNavigationHandler;
import com.wallet.presentation.Localisation;

import java.util.Scanner;

/**
 * Интерфейс главного меню
 */
public class UIMenu extends AbstractUI implements UI {

    /**
     * Интерфейс главного меню
     */
    public UIMenu(Scanner scanner) {
        super(scanner);
    }

    @Override
    public UI run() {
        String userInput;
        do {
            System.out.print(Localisation.START_MENU_RU);

            userInput = UserMenuNavigationHandler.menuNavigation(new String[]{"1", "2", "3", "4"}, scanner);

            switch (userInput) {
                case "1" -> {
                    return new UIAuth(this.scanner);
                }
                case "2" -> {
                    return new UIReg(this.scanner);
                }
                case "3" -> {

                }
                case "4" -> {
                    return new UIAudit(this.scanner, new PlayerDaoImpl());
                }
            }
        } while (!userInput.equals("3"));
        return null;
    }
}
