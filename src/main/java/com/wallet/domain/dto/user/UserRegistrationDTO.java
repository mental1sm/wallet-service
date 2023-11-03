package com.wallet.domain.dto.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

/**
 * Расширенный DTO игрока, включающий в себя логин, пароль, имя, фамилию, почту
 */
@Component
@Data
@EqualsAndHashCode(callSuper = false)
public class UserRegistrationDTO extends UserAuthDTO implements UserDTO {
    private String name;
    private String surname;
    private String email;
}
