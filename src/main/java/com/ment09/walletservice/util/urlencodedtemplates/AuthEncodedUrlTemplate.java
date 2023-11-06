package com.ment09.walletservice.util.urlencodedtemplates;

import com.ment09.walletservice.config.KeycloakProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthEncodedUrlTemplate {

    private final KeycloakProperties keycloakProperties;

    public MultiValueMap<String, String> authEncodedUrlBody(Map<String, Object> requestBody) {
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("grant_type", "password");
        data.add("client_id", keycloakProperties.getClientId());
        data.add("username", requestBody.get("username").toString());
        data.add("password", requestBody.get("password").toString());
        data.add("client_secret", keycloakProperties.getClientSecret());
        return data;
    }
}
