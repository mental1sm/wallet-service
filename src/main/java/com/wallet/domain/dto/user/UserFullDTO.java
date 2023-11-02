package com.wallet.domain.dto.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserFullDTO extends UserRegistrationDTO {
    private long id;
}
