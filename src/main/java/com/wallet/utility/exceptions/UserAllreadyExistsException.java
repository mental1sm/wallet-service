package com.wallet.utility.exceptions;

/**
 * Исключение, вызываемое в том случае, когда пользователь пытается использовать при регистрации уже существующий логин.
 */
public class UserAllreadyExistsException extends Exception {

    public UserAllreadyExistsException() {
        super();
    }
}
