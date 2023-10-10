package com.wallet.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Представляет сущность транзакции.
 */
@AllArgsConstructor
@Builder
@Getter
public class Transaction {

    private String walletId, playerId, transactionId;

    /**
     * Указывает на тип транзакции.
     * "Пополнение" = 1;
     * "Снятие" = 2;
     */
    private int transactionType;

    private Date transactionDate;

    /**
    * Указывает статус транзакции.
     * По умолчанию (при создании) "в обработке" = 0;
     * "одобрено" = 1;
     * "отклонено" = 2.
    */
    @Setter private int transactionStatus;
    private BigDecimal transactionSum;


}
