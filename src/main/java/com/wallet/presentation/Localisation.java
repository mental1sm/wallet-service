package com.wallet.presentation;

import com.wallet.entities.Transaction;

import java.util.HashMap;

/**
 * Содержит статические переменные с различными вариантами для вывода при составлении консольного UI
*/
public class Localisation {
    public static final String START_MENU_RU = """
                ================================
                \t\t[Главное меню]

                [1] - Авторизация
                [2] - Регистрация
                [3] - Выйти из приложения
                [4] - Журнал аудита (ADMIN) [Логин: ment09 Пароль: 123321]
                ================================
                """;

    public static final String REG_MENU_RU = """
                ================================
                \t\t[Регистрация]                
                ================================
                """;

    public static final String REG_ENTER_LOGIN_RU = "Введите логин: ";

    public static final String REG_ENTER_PASSWORD_RU = "Введите пароль: ";

    public static final String REG_ENTER_NAME_RU = "Введите ваше имя: ";

    public static final String REG_ENTER_SURNAME_RU = "Введите вашу фамилию: ";

    public static final String REG_FINISH_RU = """
                ================================
                \t[Регистрация завершена!]                
                ================================
                """;

    public static final String AUTH_MENU_RU = """
                ================================
                \t\t[Авторизация]                
                ================================
                """;

    public static final String AUTH_ENTER_LOGIN_RU = "Введите логин: ";

    public static final String AUTH_ENTER_PASSWORD_RU = "Введите пароль: ";

    public static final String AUTH_FINISH_RU = """
                ================================
                \t[Авторизация завершена!]                
                ================================
                """;

    public static final String WALLET_MENU_INFO_RU = """
                ================================
                \t[Пользователь] : [%s]
                \t[Кошелек] : [%s]
                \t[Текущий счет] : [%s]
                
                [1] - Пополнить
                [2] - Снять
                [3] - История транзакций
                [4] - Выйти
                ================================
                """;

    public static final String WALLET_MENU_DEPOSIT_HEADER_RU = """
                ================================
                \t[Пополнение]                
                ================================
                """;

    public static final String WALLET_MENU_WITHDRAW_HEADER_RU = """
                ================================
                \t[Пополнение]                
                ================================
                """;

    public static final String UTIL_LINER = "================================";

    public static final String WALLET_MENU_DEPOSIT_RU = "Введите сумму пополнения: ";
    public static final String WALLET_MENU_WITHDRAW_RU = "Введите сумму снятия: ";

    public static final String WALLET_MENU_TRANSACTION_HISTORY_HEADER_RU = """
                ================================
                \t[История транзакций]                
                ================================
                """;

    public static final String WALLET_MENU_TRANSACTION_DETAILS_RU = """
                --------------------------------
                [ID] : %s
                [Тип] : %s
                [Статус] : %s
                [Дата] : %s
                [Сумма] : %s              
                --------------------------------
                """;

    public static final String GOBACK_RU = "Введите любой символ, чтобы вернуться назад.";

    public static final String AUDIT_HEADER_RU = """
                ================================
                \t[Журнал аудита]                
                ================================
                """;

    public static final String AUDIT_LOGIN_RU = "Логин администратора: ";
    public static final String AUDIT_PASSWORD_RU = "Пароль администратора: ";
    public static final String AUDIT_INCORRECT_RU = "Такого администратора не существует.";
    public static final String AUDIT_CORRECT_RU = "<Вы вошли в журнал аудита>";

    public static final String AUDIT_SINGLE_LOG_RU = """
                ---------------------------------------------------------------------------------------------------------------------------------------------------
                \t%s               
                ---------------------------------------------------------------------------------------------------------------------------------------------------
                """;

    public static final String INCORRECT_INPUT_RU = "Ошибка ввода данных!";
    public static final String INPUT_MISMATCH_EXCEPTION_RU = "Некорректное значение ввода";
    public static final String EMPTY_TRANSACTION_HISTORY_RU = "Ваша история транзакций пока что пуста...";
    public static final String USER_IS_NOT_EXISTS_RU = "Пользователь с таким логином/паролем не найден.\n";

    public static final String USER_IS_ALREADY_EXISTS_RU = "Этот логин занят.";

    public static String transactionStatusCodeTranslateRu(Transaction.Status statusCode) {
        HashMap<Transaction.Status, String> codeDict = new HashMap<>();
        codeDict.put(Transaction.Status.Approved, "Одобрено");
        codeDict.put(Transaction.Status.Disapproved, "Отказано");
        codeDict.put(Transaction.Status.Pending, "В обработке");
        return codeDict.get(statusCode);
    }

    public static String transactionTypeCodeTranslateRu(Transaction.Type typeCode) {
        HashMap<Transaction.Type, String> codeDict = new HashMap<>();
        codeDict.put(Transaction.Type.Deposit, "Пополнение");
        codeDict.put(Transaction.Type.Withdrawing, "Снятие");
        return codeDict.get(typeCode);
    }
}
