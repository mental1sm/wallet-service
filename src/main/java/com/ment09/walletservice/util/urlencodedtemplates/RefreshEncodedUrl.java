package com.ment09.walletservice.util.urlencodedtemplates;

import com.ment09.walletservice.config.KeycloakProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class RefreshEncodedUrl {

    private final KeycloakProperties keycloakProperties;

    public MultiValueMap<String, String> refreshEncodedUrlBody(String refreshToken) {
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("grant_type", "refresh_token");
        data.add("refresh_token", refreshToken);
        data.add("client_id", keycloakProperties.getClientId());
        data.add("client_secret", keycloakProperties.getClientSecret());
        return data;
    }
}
