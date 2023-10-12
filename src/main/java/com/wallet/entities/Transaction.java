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
    public enum Type {
        Deposit,
        Withdrawing
    }

    public enum Status {
        Pending,
        Approved,
        Disapproved
    }

    private String walletId, playerId, transactionId;

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
