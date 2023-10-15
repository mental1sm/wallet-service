package com.wallet;

import com.wallet.presentation.player_interface.UIMenu;
import com.wallet.presentation.player_interface.UI;

import java.util.Scanner;


/**
 * Главный класс приложения для управления кошельком.
 */
public class WalletAppMain {
    /**
     * Главный метод, запускающий приложение.
     *
     * @param args Аргументы командной строки (не используются в этом приложении).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UI ui = new UIMenu(scanner);

        // Основной цикл приложения для переключения между интерфейсами.
        while (ui != null) {
            ui = ui.run(); // Запуск интерфейса. Каждый интерфейс возвращает следующий запускаемый интерфейс
        }
    }
}
