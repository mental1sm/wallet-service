package com.wallet.infrastructure.configs;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class KeycloakConfig implements Config {
    private static KeycloakConfig instance = null;
    private static Properties properties;
    private KeycloakConfig() {
        properties = new Properties();
        try (InputStream istream = getClass().getClassLoader().getResourceAsStream("auth/keycloak.properties"); ) {
            properties.load(istream);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static KeycloakConfig getInstance() {
        if (instance == null) { instance = new KeycloakConfig(); }
        return instance;
    }

    @Override
    public String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }

}
