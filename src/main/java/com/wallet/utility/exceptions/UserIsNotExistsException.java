package com.wallet.utility.exceptions;

/**
 * Исключение, вызываемое в том случае, когда пользователь пытается использовать при регистрации уже существующий логин.
 */
public class UserIsNotExistsException extends Exception {
    public UserIsNotExistsException() {
        super();
    }
}
