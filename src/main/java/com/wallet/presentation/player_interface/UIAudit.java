package com.wallet.presentation.player_interface;

import com.wallet.dao.player.PlayerDao;
import com.wallet.entities.Log;
import com.wallet.entities.Player;
import com.wallet.in.AdminAuthInputHandler;
import com.wallet.infrastructure.UserSession;
import com.wallet.presentation.Localisation;
import liquibase.pro.packaged.S;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
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
        if (!haveRights(userInput)) { return new UIMenu(scanner); }
        else {
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

    private boolean haveRights(HashMap<String, String> userInput) {
        Optional<Player> potentialAdmin = playerDao.findPlayer(userInput.get("login"));
        return potentialAdmin.filter(player -> player.getPermissionLevel().equals(Player.Permission.ADMIN)
                || player.getPermissionLevel().equals(Player.Permission.SUPERADMIN)).isPresent();
    }

}
