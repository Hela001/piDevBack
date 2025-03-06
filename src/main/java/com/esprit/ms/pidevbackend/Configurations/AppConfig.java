package com.esprit.ms.pidevbackend.Configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${chef.projet.email}")
    private String chefProjetEmail;

    @Bean
    public String chefProjetEmail() {
        return chefProjetEmail;
    }
}