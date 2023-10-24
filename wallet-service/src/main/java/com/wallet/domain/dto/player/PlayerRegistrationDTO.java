package com.wallet.domain.dto.player;

import lombok.Data;

@Data
public class PlayerRegistrationDTO {
    private String name;
    private String surname;
    private String login;
    private String password;
    private String email;
}
