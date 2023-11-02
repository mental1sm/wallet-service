package com.wallet.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.wallet.config.KeycloakConfig;
import com.wallet.utility.exceptions.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenContext {
    private final KeycloakConfig keycloakConfig;

    /**
     * Метод получения всего контекста авторизации
     * @return Полный контекст авторизации в формате Map String: Object
    */
    public Map<String, Object> getSecurityContext(String authHeader) throws NoSuchAlgorithmException, InvalidKeySpecException, UnauthorizedException {
        String token = extractTokenFromAuthorizationHeader(authHeader).orElseThrow(UnauthorizedException::new);
        DecodedJWT jwt = decodeAndVerify(token);
        return getContext(jwt);
    }

    public String getLoginFromContext(String authHeader) throws NoSuchAlgorithmException, InvalidKeySpecException, UnauthorizedException {
        Map<String, Object> context = getSecurityContext(authHeader);
        return context.get("preferred_username").toString();
    }

    /**
     * Декодирование и верификация токена
     * @return DecodedJWT-объект
    */
    private DecodedJWT decodeAndVerify(String token) throws UnauthorizedException, NoSuchAlgorithmException, InvalidKeySpecException {
        String publicKey = keycloakConfig.getPublicRsaKey();
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKey);
        RSAPublicKey rsaPublicKey = (RSAPublicKey) KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(publicKeyBytes));


        Algorithm algorithm = Algorithm.RSA256(rsaPublicKey, null);
        DecodedJWT jwt;
        try {
            jwt = JWT.require(algorithm).build().verify(token);
        } catch (JWTVerificationException e) {
            throw new UnauthorizedException();
        }
        return jwt;
    }

    /**
     * Извлекает из декодированного токена контекст и помещает его в Map
     * @return Map с контекстом токена
     */
    private Map<String, Object> getContext(DecodedJWT jwt) {
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
    private Optional<String> extractTokenFromAuthorizationHeader(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return Optional.of(authHeader.substring(7));
        }
        return Optional.empty();
    }

}
