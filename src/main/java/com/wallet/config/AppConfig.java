package com.wallet.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Конфигурация модулей приложения
 */
@Configuration
@EnableAspectJAutoProxy
public class AppConfig {
    @Bean
    public  Map<String, Object> yamlProperties() throws IOException {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("application.yml");
        if (inputStream == null) {
            throw new IOException("Не удается найти файл application.yml");
        }
        return yaml.load(inputStream);
    }


    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
