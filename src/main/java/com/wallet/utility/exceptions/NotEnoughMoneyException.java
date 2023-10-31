package com.wallet.utility.exceptions;

/**
 * Исключение, вызываемое в том случае, когда пользователь пытается снять больше средств, чем есть у него на счете.
*/
public class NotEnoughMoneyException extends Exception {
    public NotEnoughMoneyException() {
        super();
    }
}
