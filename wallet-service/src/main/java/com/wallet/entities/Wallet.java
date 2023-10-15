package com.wallet.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Представляет сущность кошелька.
 */
@Getter
@AllArgsConstructor
public class Wallet {
    private UUID walletId, playerId;
    @Setter private BigDecimal walletMoneyAmount;
}
