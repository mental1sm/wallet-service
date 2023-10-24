package com.wallet.presentation.servlets.client;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.wallet.infrastructure.configs.KeycloakConfig;
import jakarta.servlet.http.HttpServletRequest;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class KeycloakContextExtractor {
    /**
     * Вытаскивает из access token контекст Keycloak
    */
    public static Map<String, Object> getSecurityContextFromAccessToken(HttpServletRequest request) throws NoSuchAlgorithmException, InvalidKeySpecException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        String token = TokenExtractor.extractTokenFromAuthorizationHeader(request);
        String publicKey = KeycloakConfig.getInstance().getProperty("public_rsa_key");
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKey);
        RSAPublicKey rsaPublicKey = (RSAPublicKey) KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(publicKeyBytes));


        Algorithm algorithm = Algorithm.RSA256(rsaPublicKey, null);
        DecodedJWT jwt = JWT.require(algorithm).build().verify(token);

        Map<String, Claim> claims = jwt.getClaims();
        Map<String, Object> keycloakContext = new HashMap<>();
        for (Map.Entry<String, Claim> entry : claims.entrySet()) {
            String key = entry.getKey();
            Claim claim = entry.getValue();
            Object claimValue = claim.as(Object.class); // Преобразование Claim в Object
            keycloakContext.put(key, claimValue);
        }

        return keycloakContext;
    }

    /**
     * Извлечение токена из заголовка Authorization
    */
    private static class TokenExtractor {
        public static String extractTokenFromAuthorizationHeader(HttpServletRequest request) {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                return authHeader.substring(7);
            }
            return null;
        }
    }
}
