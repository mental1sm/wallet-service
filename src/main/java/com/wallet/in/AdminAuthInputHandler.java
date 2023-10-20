package com.wallet.in;

import com.wallet.presentation.Localisation;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Отвечает за ввод пользователя, связанный с авторизацией аккаунта администратора
 */
public class AdminAuthInputHandler {
    /**
     * Перехватывает управление консолью и предоставляет UI авторизации пользователю
     * @return Хеш-таблица данных пользователя для авторизации
     *      ключи:
     *      login
     *      password
     */
    public static HashMap<String, String> authInput(Scanner scanner) {
        System.out.print(Localisation.AUDIT_HEADER_RU);
        HashMap<String, String> inputValues = new HashMap<>();
        System.out.print(Localisation.AUDIT_LOGIN_RU);
        inputValues.put("login", scanner.next());
        System.out.print(Localisation.AUDIT_PASSWORD_RU);
        inputValues.put("password", scanner.next());
        return inputValues;
    }
}
