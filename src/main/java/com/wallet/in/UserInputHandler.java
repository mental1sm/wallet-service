package com.wallet.in;

import com.wallet.presentation.Localisation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class UserInputHandler {

    public static HashMap<String, String> regInput(Scanner scanner) {
        HashMap<String, String> inputValues = new HashMap<>();
        System.out.print(Localisation.REG_ENTER_LOGIN_RU);
        inputValues.put("login", scanner.next());
        System.out.print(Localisation.REG_ENTER_PASSWORD_RU);
        inputValues.put("password", scanner.next());
        System.out.print(Localisation.REG_ENTER_NAME_RU);
        inputValues.put("name", scanner.next());
        System.out.print(Localisation.REG_ENTER_SURNAME_RU);
        inputValues.put("surname", scanner.next());
        return inputValues;
    }

    public static HashMap<String, String> authInput(Scanner scanner) {
        HashMap<String, String> inputValues = new HashMap<>();
        System.out.print(Localisation.REG_ENTER_LOGIN_RU);
        inputValues.put("login", scanner.next());
        System.out.print(Localisation.REG_ENTER_PASSWORD_RU);
        inputValues.put("password", scanner.next());
        return inputValues;
    }

    public static String menuNavigation(String[] rawMenuPointers, Scanner scanner) {

        ArrayList<String> menuPointers = new ArrayList<>();
        menuPointers.addAll(Arrays.asList(rawMenuPointers));

        boolean correctInput = false;
        String userInput = "";

        while (!correctInput) {
            System.out.print(">> ");
            userInput = scanner.next();

            if (UtilInputCheck.checkInputMenuPointer(menuPointers, userInput)) correctInput = true;
        }
        return userInput;
    }

    public static BigDecimal inputDepositMoney(Scanner scanner) {
        System.out.print(Localisation.WALLET_MENU_DEPOSIT_RU);
        String userInput = scanner.next();
        return BigDecimal.valueOf(Double.parseDouble(userInput));
    }

    public static BigDecimal inputWithdrawMoney(Scanner scanner) {
        System.out.print(Localisation.WALLET_MENU_WITHDRAW_RU);
        String userInput = scanner.next();
        return BigDecimal.valueOf(Double.parseDouble(userInput));
    }
}
