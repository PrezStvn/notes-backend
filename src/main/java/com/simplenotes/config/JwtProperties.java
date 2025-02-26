package com.simplenotes.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    @Value("${jwt.expiration}")
    private Long expiration;
    
    @Value("${jwt.secret}")
    private String secret;

    public Long getExpiration() { return expiration; }
    public void setExpiration(Long expiration) { this.expiration = expiration; }
    public String getSecret() { return secret; }
    public void setSecret(String secret) { this.secret = secret; }
} 