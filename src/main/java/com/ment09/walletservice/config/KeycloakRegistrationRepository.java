package com.ment09.walletservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

@Configuration
@EnableWebSecurity
public class KeycloakRegistrationRepository {

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(this.keycloakRegistrationRepository());
    }

    private ClientRegistration keycloakRegistrationRepository() {
        return ClientRegistration
                .withRegistrationId("external")
                .clientId("wallet-client")
                .clientSecret("bO06LhYXKple5KnVwXCqjFE6lJkKXUnJ")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                .redirectUri("http://127.0.0.1/index")
                .scope("openid", "email", "profile")
                .authorizationUri("http://localhost:8282/realms/wallet-realm/protocol/openid-connect/auth")
                .tokenUri("http://localhost:8282/realms/wallet-realm/protocol/openid-connect/token")
                .userInfoUri("http://localhost:8282/realms/wallet-realm/protocol/openid-connect/userinfo")
                .userNameAttributeName("preferred_name")
                .jwkSetUri("http://localhost:8282/realms/wallet-realm/protocol/openid-connect/certs")
                .clientName("wallet-client")
                .build();
    }


}
