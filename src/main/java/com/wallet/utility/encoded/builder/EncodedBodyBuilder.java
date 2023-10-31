package com.wallet.utility.encoded.builder;

import com.wallet.config.KeycloakConfig;
import com.wallet.domain.dto.user.UserAuthDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class EncodedBodyBuilder {
    @Autowired
    private KeycloakConfig keycloakConfig;
    public String authEncodedString(UserAuthDTO userAuthDTO) {
        return String.format(
                "grant_type=password&client_id=wallet-client&username=%s&password=%s&client_secret=%s",
                URLEncoder.encode(userAuthDTO.getLogin(), StandardCharsets.UTF_8),
                URLEncoder.encode(userAuthDTO.getPassword(), StandardCharsets.UTF_8),
                URLEncoder.encode(keycloakConfig.getSecret(), StandardCharsets.UTF_8));
    }

    public String adminAuthEncodedString() {
        return String.format(
                "grant_type=password&client_id=admin-cli&username=%s&password=%s&client_secret=%s",
                URLEncoder.encode(keycloakConfig.getAdminUsername(), StandardCharsets.UTF_8),
                URLEncoder.encode(keycloakConfig.getAdminPassword(), StandardCharsets.UTF_8),
                URLEncoder.encode(keycloakConfig.getSecret(), StandardCharsets.UTF_8));
    }

}
