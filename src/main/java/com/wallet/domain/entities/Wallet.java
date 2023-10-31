package com.wallet.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Представляет сущность кошелька.
 */
@Getter
@AllArgsConstructor
@Builder
public class Wallet {
    private long id, playerId;
    @Setter private BigDecimal moneyAmount;
}
