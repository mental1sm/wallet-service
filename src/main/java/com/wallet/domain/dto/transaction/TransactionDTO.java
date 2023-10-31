package com.wallet.domain.dto.transaction;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wallet.domain.dto.serializers.*;
import com.wallet.domain.entities.Transaction;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TransactionDTO {
    private String id;
    private long walletId;
    private long playerId;
    @JsonSerialize(using = TransactionStatusSerializer.class)
    @JsonDeserialize(using = TransactionStatusDeserializer.class)
    private Transaction.Status transactionStatus;
    @JsonSerialize(using = TransactionTypeSerializer.class)
    @JsonDeserialize(using = TransactionStatusDeserializer.class)
    private Transaction.Type transactionType;
    @JsonSerialize(using = SumSerializer.class)
    @JsonDeserialize(using = SumDeserializer.class)
    private BigDecimal transactionSum;
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private Date transactionDate;

}
