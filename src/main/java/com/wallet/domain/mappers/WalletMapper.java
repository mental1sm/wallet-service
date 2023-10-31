package com.wallet.domain.mappers;

import com.wallet.domain.dto.wallet.WalletDTO;
import com.wallet.domain.entities.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface WalletMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "moneyAmount", source = "moneyAmount")
    WalletDTO walletToWalletDTO(Wallet wallet);
}
