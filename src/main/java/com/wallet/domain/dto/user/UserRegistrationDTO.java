package com.wallet.domain.dto.user;

import lombok.Data;

@Data
public class UserRegistrationDTO {
    private String name;
    private String surname;
    private String login;
    private String password;
    private String email;
}
