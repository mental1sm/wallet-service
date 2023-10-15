package com.wallet.utility.exceptions;

/**
 * Исключение, вызываемое в том случае, когда пользователь пытается использовать при регистрации уже существующий логин.
 */
public class PlayerAllreadyExistsException extends Exception {

    public PlayerAllreadyExistsException(String msg) {
        super(msg);
    }
}
