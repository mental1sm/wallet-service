package com.wallet.presentation.player_interface;

import com.wallet.dao.player.PlayerDao;
import com.wallet.entities.Player;
import com.wallet.in.AdminAuthInputHandler;
import com.wallet.presentation.Localisation;
import com.wallet.utility.exceptions.PlayerIsNotExistsException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Интерфейс журнала аудита
*/
public class UIAudit extends AbstractUI implements UI {

    PlayerDao playerDao;

    /**
     * Интерфейс журнала аудита
     */
    public UIAudit(Scanner scanner, PlayerDao playerDao) {
        super(scanner);
        this.playerDao = playerDao;
    }

    @Override
    public UI run() {
        HashMap<String, String> userInput = AdminAuthInputHandler.authInput(scanner);
        Player admin;

        try {
            admin = playerDao.findPlayer(userInput.get("login"), userInput.get("password"));
        } catch (PlayerIsNotExistsException e) {
            System.out.println(Localisation.AUDIT_INCORRECT_RU);
            return new UIMenu(scanner);
        }
        if (admin.getPermission().equals(Player.Permission.ADMIN)) {
            System.out.println(Localisation.AUDIT_CORRECT_RU);
            System.out.println(Localisation.AUDIT_HEADER_RU);
            ArrayList<String> auditLogs = loggerService.getAllLogs();
            for (String log : auditLogs) {
                System.out.printf(Localisation.AUDIT_SINGLE_LOG_RU, log);
            }
            System.out.println(Localisation.UTIL_LINER);
            System.out.println(Localisation.GOBACK_RU);
            scanner.next();
        }
        return new UIMenu(scanner);
    }
}
