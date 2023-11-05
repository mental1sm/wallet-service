package com.ment09.walletservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
@Slf4j
public class AppController {

    @GetMapping("/")
    public String getHome() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String getIndex() {
        return "index";
    }

    //@Secured("ROLE_CLIENT")
    //@PreAuthorize("hasRole('client')")
    @GetMapping("/secured")
    public String getSecuredIndex() {
        System.out.println(SecurityContextHolder.getContext());
        return "index-secured";
    }

    @GetMapping("/account/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DefaultOidcUser oidcUser = (DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String idTokenHint = oidcUser.getIdToken().getTokenValue();
        HttpSession session= request.getSession(false);
        SecurityContextHolder.clearContext();
        if(session != null) {
            session.invalidate();
        }
        String responseString = String.format("http://localhost:8282/realms/wallet-realm/protocol/openid-connect/logout?id_token_hint=%s&post_logout_redirect_uri=http://localhost:8080/", idTokenHint);
        response.sendRedirect(responseString);
    }
}
