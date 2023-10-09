package com.wallet.in;

import com.wallet.presentation.Localisation;

import java.util.HashMap;
import java.util.Scanner;

public class UserAuthInputHandler {
    /**
     * Перехватывает управление консолью и предоставляет UI авторизации пользователю
     * @return Хеш-таблица данных пользователя для авторизации
     *      ключи:
     *      login
     *      password
     */
    public static HashMap<String, String> authInput(Scanner scanner) {
        System.out.print(Localisation.AUTH_MENU_RU);
        HashMap<String, String> inputValues = new HashMap<>();
        System.out.print(Localisation.REG_ENTER_LOGIN_RU);
        inputValues.put("login", scanner.next());
        System.out.print(Localisation.REG_ENTER_PASSWORD_RU);
        inputValues.put("password", scanner.next());
        return inputValues;
    }
}
