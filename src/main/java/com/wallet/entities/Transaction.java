package com.wallet.entities;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;


@Getter
public class Transaction {

    private final String walletId, playerId, transactionId;

    /**
     * Указывает на тип транзакции.
     * "Пополнение" - 1;
     * "Снятие" - 2;
     */
    private final int transactionType;

    private final Date transactionDate;

    /**
    * Указывает статус транзакции.
     * По умолчанию (при создании) "в обработке" - 0;
     * "одобрено" - 1;
     * "отклонено" - 2.
    */
    @Setter private int transactionStatus;
    private BigDecimal transactionSum;

    public Transaction(String walletId, String playerId, String transactionId, int transactionType, int transactionStatus, BigDecimal transactionSum) {
        this.walletId = walletId;
        this.playerId = playerId;
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.transactionStatus = transactionStatus;
        this.transactionDate = new Date();
        this.transactionSum = transactionSum;
    }


}
