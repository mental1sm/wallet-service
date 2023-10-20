package com.wallet.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * Представляет сущность транзакции.
 */
@AllArgsConstructor
@Builder
@Getter
public class Transaction {
    public enum Type {
        Deposit,
        Withdrawing
    }

    public enum Status {
        Pending,
        Approved,
        Disapproved
    }

    private UUID transactionId;
    private long walletId, playerId;

    /**
     * Указывает на тип транзакции.
     * "Пополнение"
     * "Снятие"
     */
    private Type transactionType;

    /**
    * Указывает статус транзакции.
     * По умолчанию (при создании) -> "в обработке"
     * "одобрено"
     * "отклонено"
    */
    @Setter private Status transactionStatus;
    private BigDecimal transactionSum;
    private Date transactionDate;


}
