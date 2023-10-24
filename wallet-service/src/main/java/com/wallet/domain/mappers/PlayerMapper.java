package com.wallet.domain.mappers;

import com.wallet.domain.dto.player.PlayerAuthDTO;
import com.wallet.domain.dto.player.PlayerDTO;
import com.wallet.domain.dto.player.PlayerRegistrationDTO;
import com.wallet.domain.entities.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PlayerMapper {

    @Mapping(target = "login", source = "login")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "surname", source = "surname")
    @Mapping(target = "email", source = "email")
    PlayerDTO regPlayerToPlayerDTO(PlayerRegistrationDTO playerRegistrationDTO);

    @Mapping(target = "login", source = "login")
    @Mapping(target = "password", source = "password")
    PlayerDTO authPlayerToPlayerDTO(PlayerAuthDTO playerAuthDTO);
}
