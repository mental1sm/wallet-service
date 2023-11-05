package com.ment09.walletservice.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class AuthHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
        List<String> roles = (List<String>) oidcUser.getClaims().get("roles");

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if (roles != null) {
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
        }

        // Создаем новый объект аутентификации с обновленными authorities
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), authorities);

        // Устанавливаем новый объект аутентификации в SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        super.onAuthenticationSuccess(request, response, newAuthentication);

    }
}
