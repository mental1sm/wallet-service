package com.ment09.walletservice.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@PropertySource("/application.properties")
public class KeycloakProperties {
    @Value("${keycloak.client-id}")
    private String clientId;
    @Value("${keycloak.token-url}")
    private String tokenUrl;
    @Value("${keycloak.introspect-url}")
    private String introspectUrl;
    @Value("${keycloak.client-secret}")
    private String clientSecret;
}
