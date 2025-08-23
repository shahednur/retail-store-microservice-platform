package com.shahed.notificationservice.config;

import org.springframework.context.annotation.Configuration;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;

@Configuration
public class DotenvConfig {

    @PostConstruct
    public void loadEnv() {
        try {
            Dotenv dotenv = Dotenv.configure()
                    .directory("./") // Look for .env in project root
                    .load();

            // Set system properties so Spring can access them
            dotenv.entries().forEach(entry -> {
                System.setProperty(entry.getKey(), entry.getValue());
            });

            System.out.println("✅ Environment variables loaded from .env file");
        } catch (Exception e) {
            System.err.println("⚠️ Could not load .env file: " + e.getMessage());
        }
    }
}
