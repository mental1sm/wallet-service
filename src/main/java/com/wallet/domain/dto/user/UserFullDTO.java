package com.wallet.domain.dto.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

/**
 * Расширенный DTO игрока, включающий в себя логин, пароль, имя, фамилию, почту и идентификатор (id)
 */
@Component
@Data
@EqualsAndHashCode(callSuper = false)
public class UserFullDTO extends UserRegistrationDTO {
    private long id;
}
