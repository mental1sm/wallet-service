package com.wallet.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Конфигурация раздачи env-переменных для Keycloak
 */
@Data
@Configuration
public class KeycloakConfig {
    private String adminUsername;
    private String adminPassword;
    private String secret;
    private String publicRsaKey;
    private String clientUrl;
    private String adminUrl;
    private String usersUrl;
    @Autowired
    public KeycloakConfig(@Qualifier("yamlProperties") Map<String, Object> yamlProperties) {
        Map<String, Object> yaml = (Map<String, Object>) yamlProperties.get("yamlProperties");
        Map<String, Object> keycloak = (Map<String, Object>) yaml.get("keycloak");

        this.adminUsername = (String) keycloak.get("admin_username");
        this.adminPassword = keycloak.get("admin_password").toString();
        this.secret = (String) keycloak.get("secret");
        this.publicRsaKey = (String) keycloak.get("public_rsa_key");
        this.clientUrl = (String) keycloak.get("client_url");
        this.adminUrl = (String) keycloak.get("admin_url");
        this.usersUrl = (String) keycloak.get("users_url");
    }


}
