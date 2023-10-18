package com.wallet.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Представляет сущность кошелька.
 */
@Getter
@AllArgsConstructor
@Builder
public class Wallet {
    private long walletId, playerId;
    @Setter private BigDecimal walletMoneyAmount;
}
