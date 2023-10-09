package com.wallet.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Представляет сущность игрока.
*/
@Getter
@AllArgsConstructor
public class Player {

    final private String name, surname, playerID, pLogin;
    @Setter private String pPassword;


}
