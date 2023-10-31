package com.wallet.domain.dto.transaction;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wallet.domain.dto.serializers.*;
import com.wallet.domain.entities.Transaction;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class NewTransactionDTO {
    @JsonSerialize(using = TransactionTypeSerializer.class)
    @JsonDeserialize(using = TransactionTypeDeserializer.class)
    private Transaction.Type transactionType;
    @JsonSerialize(using = SumSerializer.class)
    @JsonDeserialize(using = SumDeserializer.class)
    private BigDecimal transactionSum;
}
