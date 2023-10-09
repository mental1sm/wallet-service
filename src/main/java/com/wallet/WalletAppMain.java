package com.wallet;

import com.wallet.presentation.player_interface.InterfaceMenu;
import com.wallet.presentation.player_interface.iInterface;

import java.util.Scanner;


public class WalletAppMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Инициализация сканнера
        iInterface ui = new InterfaceMenu(scanner); // Инициализация общего интерфейса для запуска прочих UI
        while (ui != null) {
            ui = ui.run(); // Запуск интерфейса. Каждый интерфейс возвращает следующий запускаемый интерфейс
        }
    }
}
