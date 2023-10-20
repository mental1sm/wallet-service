package com.wallet.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Представляет сущность игрока.
*/
@Getter
@AllArgsConstructor
@Builder
public class Player {
    public static enum Permission {
        USER,
        ADMIN,
        SUPERADMIN
    }

    final private long id;
    @Setter private int permissionId;
    final private Permission permissionLevel;
    final private String name, surname, login;
    @Setter private String password;


}
