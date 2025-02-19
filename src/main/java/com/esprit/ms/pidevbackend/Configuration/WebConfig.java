package com.esprit.ms.pidevbackend.Configuration;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow CORS requests from your Angular frontend
        registry.addMapping("/**") // Allow CORS for all endpoints under /Api/
                .allowedOrigins("http://localhost:4200") // Your Angular app URL (adjust accordingly for prod)
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Allowed methods (GET, POST, PUT, DELETE)
                .allowedHeaders("*") // Allow all headers
                .allowCredentials(true); // If you want to send cookies, set this to true
    }
}
