package com.wallet.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

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

    final private long playerID;
    final private int permissionId;
    final private Permission permission;
    final private String name, surname, pLogin;
    @Setter private String pPassword;


}
