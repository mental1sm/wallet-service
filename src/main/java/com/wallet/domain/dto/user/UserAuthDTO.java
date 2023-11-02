package com.wallet.domain.dto.user;

import lombok.Data;

@Data
public class UserAuthDTO implements UserDTO {
    private String login;
    private String password;
}
