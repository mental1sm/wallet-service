package com.wallet.domain.dto.user;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Самый базовый DTO игрока, включающий в себя логин и пароль
 */
@Component
@Data
public class UserAuthDTO implements UserDTO {
    private String login;
    private String password;
}
