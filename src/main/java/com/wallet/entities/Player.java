package com.wallet.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Представляет сущность игрока.
*/
@Getter
@AllArgsConstructor
public class Player {
    public static enum Permission {
        USER,
        ADMIN,
        SUPERADMIN
    }

    final private UUID playerID;
    final private Permission permission;
    final private String name, surname, pLogin;
    @Setter private String pPassword;


}
