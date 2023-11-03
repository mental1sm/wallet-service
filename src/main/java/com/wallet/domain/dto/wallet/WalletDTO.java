package com.wallet.domain.dto.wallet;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wallet.domain.dto.serializers.SumDeserializer;
import com.wallet.domain.dto.serializers.SumSerializer;
import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO кошелька пользователя
 */
@Data
public class WalletDTO {
    private long id;
    @JsonSerialize(using = SumSerializer.class)
    @JsonDeserialize(using = SumDeserializer.class)
    private BigDecimal moneyAmount;
}
