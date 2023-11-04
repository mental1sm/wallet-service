package com.ment09.walletservice.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final TokenConverter tokenConverter;
    private final AuthenticationProvider provider;
    private final AuthenticationManager manager;
    private final ClientRegistrationRepository repository;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers("/index/**").permitAll()
                            .requestMatchers("/secured/**").authenticated()
                            .anyRequest().permitAll();
                })
                .sessionManagement(management -> {management.sessionCreationPolicy(SessionCreationPolicy.STATELESS);})
                .oauth2ResourceServer(oauth2 -> {
                    oauth2.jwt(jwtConfigurer -> {
                        jwtConfigurer.jwtAuthenticationConverter(this.tokenConverter)
                                .jwkSetUri("http://localhost:8282/realms/wallet-realm/protocol/openid-connect/certs")
                                .authenticationManager(this.manager);
                    });
                });
        return http.build();
    }

}
