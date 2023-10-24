package com.wallet.domain.mappers;

import com.wallet.domain.dto.transaction.TransactionDTO;
import com.wallet.domain.entities.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TransactionMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "walletId", source = "walletId")
    @Mapping(target = "playerId", source = "playerId")
    @Mapping(target = "transactionStatus", source = "transactionStatus")
    @Mapping(target = "transactionType", source = "transactionType")
    @Mapping(target = "transactionSum", source = "transactionSum")
    @Mapping(target = "transactionDate", source = "transactionDate")
    TransactionDTO transactionToTransactionDTO(Transaction transaction);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "walletId", source = "walletId")
    @Mapping(target = "playerId", source = "playerId")
    @Mapping(target = "transactionStatus", source = "transactionStatus")
    @Mapping(target = "transactionType", source = "transactionType")
    @Mapping(target = "transactionSum", source = "transactionSum")
    @Mapping(target = "transactionDate", source = "transactionDate")
    Transaction transactionDTOToTransaction(TransactionDTO transactionDTO);
}
