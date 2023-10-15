package com.example.weather.service;

import com.example.weather.dto.LocationDTO;
import com.example.weather.models.Location;
import com.example.weather.models.UserLocation;
import com.example.weather.models.entity.ForecastApiResponse;
import com.example.weather.models.entity.Main;
import com.example.weather.models.entity.Weather;
import com.example.weather.models.entity.WeatherApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class WeatherApiService {

    private static String API_KEY;
    private static final String GET_SEARCH_URL_FIRST_PART = "http://api.openweathermap.org/geo/1.0/direct?q=";
    private static final String GET_SEARCH_URL_SECOND_PART = "&limit=1&appid=";
    private static final String GET_LOAD_URL_FIRST_PART = "https://api.openweathermap.org/data/2.5/weather?";
    private static final String FORECAST = "https://api.openweathermap.org/data/2.5/forecast?";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${api.key}")
    public void SetApiKey(String apiKey) {
        WeatherApiService.API_KEY = apiKey;
    }

    //    http://api.openweathermap.org/geo/1.0/direct?q=London&limit=5&appid={API key} - HTTP location request example
    //    https://api.openweathermap.org/data/2.5/weather?lat=33.44&lon=-94.04&appid={API key} - HTTP weather request example
    //    https://api.openweathermap.org/data/2.5/forecast?lat={lat}&lon={lon}&appid={API key} - forecast request example

    public static LocationDTO getLocation(String locationName) {
        RestTemplate restTemplate = new RestTemplate();
        String url = GET_SEARCH_URL_FIRST_PART + locationName + GET_SEARCH_URL_SECOND_PART + API_KEY;
        String response = restTemplate.getForObject(url, String.class);
        if (response.equals("[]"))
            return null;
        try {
            return objectMapper.readValue(response, new TypeReference<List<LocationDTO>>() {}).get(0);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static WeatherApiResponse getWeather(UserLocation userLocation) {
        RestTemplate restTemplate = new RestTemplate();
        Location location = userLocation.getLocation();
        String url = GET_LOAD_URL_FIRST_PART + "lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&appid=" + API_KEY;
        String response = restTemplate.getForObject(url, String.class);

        try {
            return objectMapper.readValue(response, WeatherApiResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static ForecastApiResponse getForecast(UserLocation userLocation) {
        RestTemplate restTemplate = new RestTemplate();
        Location location = userLocation.getLocation();
        String url = FORECAST + "lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&appid=" + API_KEY;
        String response = restTemplate.getForObject(url, String.class);
        try {
            return objectMapper.readValue(response, ForecastApiResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
