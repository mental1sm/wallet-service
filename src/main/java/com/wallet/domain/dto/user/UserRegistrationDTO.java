package com.wallet.domain.dto.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserRegistrationDTO extends UserAuthDTO implements UserDTO {
    private String name;
    private String surname;
    private String email;
}
