package com.simplenotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.simplenotes.config.JwtProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class SimpleNotesApplication {
    public static void main(String[] args) {
        SpringApplication.run(SimpleNotesApplication.class, args);
    }
}
