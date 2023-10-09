package com.wallet.presentation.player_interface;

import java.util.Scanner;

/**
 * Обищая абстракция для всех UI интерфейсов
 */
public abstract class Interface {
    Scanner scanner;

    Interface(Scanner scanner) {
        this.scanner = scanner;
    }

}
