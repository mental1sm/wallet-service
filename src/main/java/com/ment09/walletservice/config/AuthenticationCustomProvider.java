package com.ment09.walletservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class AuthenticationCustomProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // Ваша логика проверки учетных данных
        if ("user".equals(username) && "password".equals(password)) {
            // Успешная аутентификация
            return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
        } else {
            // Неудачная аутентификация
            throw new BadCredentialsException("Неверные учетные данные");
        }
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Bean
    AuthenticationManager authenticationManager() {
        return this::authenticate;
    }

}
