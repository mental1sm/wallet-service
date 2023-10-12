package com.wallet.in;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Отвечает за ввод пользователя, связанный с навигацией в меню с ограниченным набором опций.
 */
public class UserMenuNavigationHandler {

    /**
     * Запрашивает у пользователя выбор одного из предоставленных пунктов меню
     * @param rawMenuPointers Массив строк. Представляет собой варианты для выбора
     * @return Выбранный вариант
    */
    public static String menuNavigation(String[] rawMenuPointers, Scanner scanner) {

        ArrayList<String> menuPointers = new ArrayList<>();
        menuPointers.addAll(Arrays.asList(rawMenuPointers));

        boolean correctInput = false;
        String userInput = "";

        while (!correctInput) {
            System.out.print(">> ");
            userInput = scanner.next();

            if (menuPointers.contains(userInput)) correctInput = true;
        }
        return userInput;
    }
}
