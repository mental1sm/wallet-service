package com.wallet.presentation.player_interface;

import com.wallet.dao.player.PlayerDao;
import com.wallet.entities.Log;
import com.wallet.entities.Player;
import com.wallet.in.AdminAuthInputHandler;
import com.wallet.presentation.Localisation;

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
        Player potentialAdmin;
        potentialAdmin = playerDao.findPlayer(userInput.get("login"));

        if (potentialAdmin == null) { return new UIMenu(scanner); }
        else if (!potentialAdmin.getPermissionLevel().equals(Player.Permission.USER)) {
            System.out.println(Localisation.AUDIT_CORRECT_RU);
            System.out.println(Localisation.AUDIT_HEADER_RU);

            ArrayList<Log> auditLogs = loggerService.getAllLogs(1);
            for (Log log : auditLogs) {
                System.out.printf(Localisation.AUDIT_SINGLE_LOG_RU,
                        String.format("[%s] - [id:%s] [%s] [%s]",
                        log.getTimestamp(), log.getPlayerId(), log.getAction(), log.getInfoLevel()
                        )
                );
            }

            System.out.println(Localisation.UTIL_LINER);
            System.out.println(Localisation.GOBACK_RU);
            scanner.next();
        }
        return new UIMenu(scanner);
    }
}
