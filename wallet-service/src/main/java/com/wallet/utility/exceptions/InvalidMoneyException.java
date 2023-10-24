package com.wallet.utility.exceptions;

/**
 * Исключение, вызываемое в том случае, когда пользователь вводит вместо числа некорректные данные.
 */
public class InvalidMoneyException extends Exception {
    public InvalidMoneyException() {
        super();
    }
}
