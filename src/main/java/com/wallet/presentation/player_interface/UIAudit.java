package com.wallet.presentation.player_interface;

import com.wallet.presentation.Localisation;

import java.util.ArrayList;
import java.util.Scanner;

public class UIAudit extends AbstractUI implements UI {

    public UIAudit(Scanner scanner) {
        super(scanner);
    }

    @Override
    public UI run() {
        System.out.println(Localisation.AUDIT_HEADER_RU);
        ArrayList<String> auditLogs = loggerService.getAllLogs();
        for (String log : auditLogs) {
            System.out.printf(Localisation.AUDIT_SINGLE_LOG_RU, log);
        }
        System.out.println(Localisation.UTIL_LINER);
        System.out.println(Localisation.GOBACK_RU);
        scanner.next();
        return new UIMenu(scanner);
    }
}
