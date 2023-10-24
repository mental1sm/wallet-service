package com.wallet.presentation.servlets.client;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.keycloak.KeycloakSecurityContext;

import java.io.IOException;
import java.io.PrintWriter;

public class AdminOnlyTestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("req = " + req + ", resp = " + resp);
        PrintWriter printWriter = resp.getWriter();

        if (isAdmin(req)) {
            printWriter.write("Hallo World protected!");
        } else {
            printWriter.write("You have not access to this page!");
        }

        }

    private boolean isAdmin(HttpServletRequest request) {
        KeycloakSecurityContext keycloakSecurityContext = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
        return keycloakSecurityContext.getToken().getRealmAccess().isUserInRole("admin");
    }
}
