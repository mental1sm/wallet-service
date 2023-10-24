package com.wallet.domain.entities;

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
    final private long id;
    final private String name, surname, login;
    final private String email;
    @Setter private String password;
}
