package com.simplenotes.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private Long expiration;
    private String secret;

    public Long getExpiration() { return expiration; }
    public void setExpiration(Long expiration) { this.expiration = expiration; }
    public String getSecret() { return secret; }
    public void setSecret(String secret) { this.secret = secret; }
} 