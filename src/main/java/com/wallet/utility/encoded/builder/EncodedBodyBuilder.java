package com.wallet.utility.encoded.builder;

import com.wallet.config.KeycloakConfig;
import com.wallet.domain.dto.user.UserAuthDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Предоставляет готовые body для ответа
*/
@Component
@AllArgsConstructor
public class EncodedBodyBuilder {

    private KeycloakConfig keycloakConfig;

    /**
     * Предоставляет encoded-строку для авторизации
     */
    public String authEncodedString(UserAuthDTO userAuthDTO) {
        return String.format(
                "grant_type=password&client_id=wallet-client&username=%s&password=%s&client_secret=%s",
                URLEncoder.encode(userAuthDTO.getLogin(), StandardCharsets.UTF_8),
                URLEncoder.encode(userAuthDTO.getPassword(), StandardCharsets.UTF_8),
                URLEncoder.encode(keycloakConfig.getSecret(), StandardCharsets.UTF_8));
    }

    /**
     * Предоставляет encoded-строку для авторизации администратора
     */
    public String adminAuthEncodedString() {
        return String.format(
                "grant_type=password&client_id=admin-cli&username=%s&password=%s&client_secret=%s",
                URLEncoder.encode(keycloakConfig.getAdminUsername(), StandardCharsets.UTF_8),
                URLEncoder.encode(keycloakConfig.getAdminPassword(), StandardCharsets.UTF_8),
                URLEncoder.encode(keycloakConfig.getSecret(), StandardCharsets.UTF_8));
    }

}
