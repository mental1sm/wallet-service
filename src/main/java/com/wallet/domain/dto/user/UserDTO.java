package com.wallet.domain.dto.user;

import lombok.Data;

@Data
public class UserDTO {
    private String name;
    private String surname;
    private String login;
    private String password;
    private long id;
    private String email;
}
