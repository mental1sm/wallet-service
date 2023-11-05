package com.ment09.walletservice.util.requests;

import com.ment09.walletservice.config.KeycloakProperties;
import com.ment09.walletservice.util.urlencodedtemplates.AuthEncodedUrlTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthTokenRequest {

    private final AuthEncodedUrlTemplate encodedUrlTemplate;
    private final KeycloakProperties keycloakProperties;

    @Bean
    public ResponseEntity<String> getAuthToken(Map<String, Object> body) {
        RestTemplate authTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> data = encodedUrlTemplate.authEncodedUrlBody(body);
        HttpEntity<MultiValueMap<String, String>> requestHttpEntity = new HttpEntity<>(data, headers);
        String authServerEndpointUrl = keycloakProperties.getTokenUrl();
        return authTemplate.postForEntity(authServerEndpointUrl, requestHttpEntity, String.class);
    }
}
