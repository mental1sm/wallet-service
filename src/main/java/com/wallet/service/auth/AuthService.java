package com.wallet.service.auth;

import com.wallet.domain.dto.user.UserAuthDTO;
import com.wallet.domain.dto.user.UserRegistrationDTO;
import com.wallet.utility.exceptions.UserAllreadyExistsException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public interface AuthService {
    /**
     * Обратиться к серверу аутентификации для получения токена пользователя.
     * @param userAuthDTO DTO авторизации
     * @return Access Token в виде строки.
     */
    Optional<String> getAuthToken(UserAuthDTO userAuthDTO) throws IOException;
    void regUserInRealm(UserRegistrationDTO regDTO) throws UserAllreadyExistsException, IOException;

}
