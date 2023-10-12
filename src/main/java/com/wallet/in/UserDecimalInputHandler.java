package com.wallet.in;

import com.wallet.presentation.Localisation;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * Отвечает за ввод пользователя, связанный с суммами для кошелька.
*/
public class UserDecimalInputHandler {

    /**
     * Запрашивает у пользователя ввод суммы для пополнения
     * @return Сумму типа данных BigDecimal
    */
    public static BigDecimal inputDepositMoney(Scanner scanner) {
        System.out.print(Localisation.WALLET_MENU_DEPOSIT_RU);
        String userInput = scanner.next();
        return BigDecimal.valueOf(Double.parseDouble(userInput));
    }

    /**
     * Запрашивает у пользователя ввод суммы для снятия
     * @return Сумму типа данных BigDecimal
     */
    public static BigDecimal inputWithdrawMoney(Scanner scanner) {
        System.out.print(Localisation.WALLET_MENU_WITHDRAW_RU);
        String userInput = scanner.next();
        return BigDecimal.valueOf(Double.parseDouble(userInput));
    }
}
