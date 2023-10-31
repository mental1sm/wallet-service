package com.wallet.domain.dto.player;

import lombok.Data;

@Data
public class PlayerDTO {
    private String name;
    private String surname;
    private String login;
    private String password;
    private long id;
    private String email;
}
