package com.ment09.walletservice.util.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ment09.walletservice.config.KeycloakProperties;
import com.ment09.walletservice.util.urlencodedtemplates.CookieExtractor;
import com.ment09.walletservice.util.urlencodedtemplates.IntrospectEncodedUrl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Component
@RequiredArgsConstructor
public class IntrospectTokenRequest {

    private final KeycloakProperties keycloakProperties;
    private final CookieExtractor cookieExtractor;
    private final IntrospectEncodedUrl encodedUrl;
    private final ObjectMapper objectMapper;

    public boolean introspectAccessToken(HttpServletRequest request) throws JsonProcessingException {
        String potentialAccessToken = cookieExtractor.extractValue(request, "ACCESS_TOKEN").orElse("NONE");
        RestTemplate introspectTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> data = encodedUrl.introspectEncodedUrlBody(potentialAccessToken);
        HttpEntity<MultiValueMap<String, String>> requestHttpEntity = new HttpEntity<>(data, headers);
        String introspectionEndpointUrl = keycloakProperties.getIntrospectUrl();
        String responseBody = introspectTemplate.postForEntity(introspectionEndpointUrl, requestHttpEntity, String.class).getBody();
        JsonNode responseNode = objectMapper.readTree(responseBody);
        return responseNode.get("active").toString().contentEquals("true");
    }
}
