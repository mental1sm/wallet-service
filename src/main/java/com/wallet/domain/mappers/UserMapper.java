package com.wallet.domain.mappers;

import com.wallet.domain.dto.user.UserAuthDTO;
import com.wallet.domain.dto.user.UserRegistrationDTO;
import com.wallet.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "login", source = "login")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "surname", source = "surname")
    @Mapping(target = "email", source = "email")
    User PlayerDTOToPlayerReg(UserRegistrationDTO userRegistrationDTO);

    @Mapping(target = "login", source = "login")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "surname", source = "surname")
    @Mapping(target = "email", source = "email")
    UserRegistrationDTO PlayerRegToPlayerDTO(User player);

    @Mapping(target = "login", source = "login")
    @Mapping(target = "password", source = "password")
    UserAuthDTO playerAuthToPlayerDTO(User player);

    @Mapping(target = "login", source = "login")
    @Mapping(target = "password", source = "password")
    User playerDTOToPlayerAuth (UserAuthDTO userAuthDTO);
}
