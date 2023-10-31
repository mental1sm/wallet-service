package com.wallet.utility.json.builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wallet.domain.dto.user.UserRegistrationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JsonBodyBuilder {
    private final ObjectMapper objectMapper;

    public String buildJsonFromMap(Map<String, Object> data) throws IOException {
        return objectMapper.writeValueAsString(data);
    }

    public String userRegistrationBody(UserRegistrationDTO userRegistrationDTO) throws IOException {
        ObjectNode json = objectMapper.createObjectNode();
        json.put("username", userRegistrationDTO.getLogin());
        json.put("email", userRegistrationDTO.getEmail());
        json.put("firstName", userRegistrationDTO.getName());
        json.put("lastName", userRegistrationDTO.getSurname());
        json.put("enabled", "true");
        ArrayNode credentialsNode = objectMapper.createArrayNode();
        ObjectNode credentialsObject = objectMapper.createObjectNode();
        credentialsObject.put("type", "password");
        credentialsObject.put("value", userRegistrationDTO.getPassword());
        credentialsNode.add(credentialsObject);
        json.set("credentials", credentialsNode);

        return objectMapper.writeValueAsString(json);
    }
}
