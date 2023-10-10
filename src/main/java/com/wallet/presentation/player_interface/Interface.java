package com.wallet.presentation.player_interface;

import com.wallet.services.loggerService.LoggerService;

import java.util.Scanner;

/**
 * Обищая абстракция для всех UI интерфейсов
 */
public abstract class Interface {
    protected Scanner scanner;
    protected LoggerService loggerService;

    Interface(Scanner scanner) {
        this.scanner = scanner;
        this.loggerService = LoggerService.getInstance();
    }

}
