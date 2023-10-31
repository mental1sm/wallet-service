package com.wallet.service.auth;

import com.wallet.config.DatabaseConfig;
import com.wallet.config.KeycloakConfig;
import com.wallet.domain.dto.user.UserAuthDTO;
import com.wallet.domain.dto.user.UserRegistrationDTO;
import com.wallet.utility.TokenUtil;
import com.wallet.utility.exceptions.UserAllreadyExistsException;
import com.wallet.utility.json.builder.JsonBodyBuilder;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final KeycloakConfig keycloakConfig;
    private final TokenUtil tokenUtil;
    private final JsonBodyBuilder jsonBodyBuilder;

    @Override
    public Optional<String> getAuthToken(UserAuthDTO userAuthDTO) throws IOException {
        return tokenUtil.getAuthToken(userAuthDTO);
    }

    @Override
    public void regUserInRealm(UserRegistrationDTO regDTO) throws UserAllreadyExistsException, IOException {
        URL url = new URL(keycloakConfig.getUsersUrl());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", MediaType.APPLICATION_JSON);
        connection.setRequestProperty("Authorization", "Bearer " + tokenUtil.getAdminToken());
        connection.setDoOutput(true);
        String jsonString = jsonBodyBuilder.userRegistrationBody(regDTO);
        try (OutputStream oStream = connection.getOutputStream()) {
            byte[] input = jsonString.getBytes();
            oStream.write(input, 0, input.length);
        }
        if (connection.getResponseCode() == HttpURLConnection.HTTP_CONFLICT) {
            throw new UserAllreadyExistsException();
        }
    }
}
