package com.ment09.walletservice.util.requests;

import com.ment09.walletservice.config.KeycloakProperties;
import com.ment09.walletservice.util.urlencodedtemplates.CookieExtractor;
import com.ment09.walletservice.util.urlencodedtemplates.RefreshEncodedUrl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Component
@RequiredArgsConstructor
public class RefreshTokenRequest {

    private final RefreshEncodedUrl refreshEncodedUrl;
    private final CookieExtractor cookieExtractor;
    private final KeycloakProperties keycloakProperties;

    public ResponseEntity<String> refreshToken(HttpServletRequest request) {
        String potentialRefreshToken = cookieExtractor.extractValue(request, "REFRESH_TOKEN").orElse("NONE");
        RestTemplate refreshTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> data = refreshEncodedUrl.refreshEncodedUrlBody(potentialRefreshToken);
        HttpEntity<MultiValueMap<String, String>> requestHttpEntity = new HttpEntity<>(data, headers);
        String tokenEndpointUrl = keycloakProperties.getTokenUrl();
        return refreshTemplate.postForEntity(tokenEndpointUrl, requestHttpEntity, String.class);
    }
}
