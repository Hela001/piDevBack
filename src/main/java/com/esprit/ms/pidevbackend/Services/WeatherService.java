package com.esprit.ms.pidevbackend.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${openweathermap.api.key}") // Clé API stockée dans application.properties
    private String apiKey; // Clé API stockée dans application.properties

    // URL de l'API sans spécifier la ville
    private String weatherApiUrl = "https://api.openweathermap.org/data/2.5/forecast";

    public String getWeatherForecast(double latitude, double longitude) {
        // Construction de l'URL en utilisant la latitude, longitude et la clé API
        String url = String.format("%s?lat=%f&lon=%f&appid=%s&units=metric", weatherApiUrl, latitude, longitude, apiKey);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }
}
