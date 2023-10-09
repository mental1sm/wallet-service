package com.wallet.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Представляет сущность кошелька.
 */
@Getter
@AllArgsConstructor
public class Wallet {
    private String walletId, playerId;
    @Setter private BigDecimal walletMoneyAmount;
}
