package com.wallet.domain.dto.user;

/**
 * Самый базовый DTO игрока, включающий в себя логин и пароль
 */
public interface UserDTO {
    String getLogin();
    String getPassword();
}
