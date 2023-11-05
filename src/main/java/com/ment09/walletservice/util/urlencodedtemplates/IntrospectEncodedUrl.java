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
public class IntrospectEncodedUrl {

    private final KeycloakProperties keycloakProperties;

    @Bean
    public MultiValueMap<String, String> introspectEncodedUrlBody(Map<String, Object> requestBody) {
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("token", requestBody.get("token").toString());
        data.add("client_id", keycloakProperties.getClientId());
        data.add("client_secret", keycloakProperties.getClientSecret());
        return data;
    }
}
