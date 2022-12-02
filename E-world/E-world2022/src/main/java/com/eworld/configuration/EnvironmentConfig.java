package com.eworld.configuration;

import com.eworld.configuration.security.SecurityEnvironment;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvironmentConfig {

    @Bean
    @ConfigurationProperties(prefix = "security")
    public SecurityEnvironment securityEnvironment(){
        return new SecurityEnvironment();
    }
}
