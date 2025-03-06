package com.esprit.ms.pidevbackend.Services;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${openweathermap.api.key}")
    private String apiKey;

    private String weatherApiUrl = "https://api.openweathermap.org/data/2.5/forecast";

    public String getWeatherForecast(double latitude, double longitude) {
        String url = String.format("%s?lat=%f&lon=%f&appid=%s&units=metric", weatherApiUrl, latitude, longitude, apiKey);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }

    public boolean isBadWeather(String weatherJson) {
        JSONObject json = new JSONObject(weatherJson);
        String weatherCondition = json.getJSONArray("list")
                .getJSONObject(0)
                .getJSONArray("weather")
                .getJSONObject(0)
                .getString("main");


        return true  ;
       // weatherCondition.equalsIgnoreCase("Rain") ||
        // weatherCondition.equalsIgnoreCase("Storm") ||
        // weatherCondition.equalsIgnoreCase("Snow") ||
        // weatherCondition.equalsIgnoreCase("Thunderstorm") ||
        // weatherCondition.equalsIgnoreCase("Extreme");
    }
}
