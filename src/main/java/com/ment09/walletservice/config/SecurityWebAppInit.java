package com.ment09.walletservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

@Configuration
@EnableAspectJAutoProxy
public class SecurityWebAppInit extends AbstractSecurityWebApplicationInitializer {
    public SecurityWebAppInit() {
        super(SecurityConfig.class);
    }
}
