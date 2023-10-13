package com.wallet.presentation.player_interface;

import com.wallet.dao.player.PlayerDaoImpl;
import com.wallet.dao.wallet.WalletDaoImpl;
import com.wallet.infrastructure.LoggerInMemoryRepository;
import com.wallet.services.loggerService.LoggerService;

import java.util.Scanner;

/**
 * Обищая абстракция для всех UI интерфейсов
 */
public abstract class AbstractUI {
    protected Scanner scanner;
    protected LoggerService loggerService;

    AbstractUI(Scanner scanner) {
        this.scanner = scanner;
        this.loggerService = LoggerService.getInstance(LoggerInMemoryRepository.getInstance(), new PlayerDaoImpl(), new WalletDaoImpl());
    }

}