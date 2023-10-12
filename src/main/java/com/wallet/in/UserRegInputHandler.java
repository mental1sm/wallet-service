package com.wallet.in;

import com.wallet.presentation.Localisation;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Отвечает за ввод пользователя, связанный с регистрацией.
 */
public class UserRegInputHandler {

    /**
     * Перехватывает управление консолью и предоставляет UI регистрации пользователю
     * @return Хеш-таблица данных пользователя для регистрации
     *      ключи:
     *      login
     *      password
     *      name
     *      surname
     */
    public static HashMap<String, String> regInput(Scanner scanner) {
        HashMap<String, String> inputValues = new HashMap<>();
        System.out.print(Localisation.REG_MENU_RU);
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
}
