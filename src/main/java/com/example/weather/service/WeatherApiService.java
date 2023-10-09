package com.example.weather.service;

import com.example.weather.dto.LocationDTO;
import com.example.weather.models.Location;
import com.example.weather.models.UserLocation;
import com.example.weather.models.entity.WeatherApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class WeatherApiService {

    private static final String API_KEY = "a8fb3b67da5736ba5a1028e989bb34c2";
    private static final String GET_SEARCH_URL_FIRST_PART = "http://api.openweathermap.org/geo/1.0/direct?q=";
    private static final String GET_SEARCH_URL_SECOND_PART = "&limit=1&appid=";
    private static final String GET_LOAD_URL_FIRST_PART = "https://api.openweathermap.org/data/2.5/weather?";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    //    http://api.openweathermap.org/geo/1.0/direct?q=London&limit=5&appid={API key} - HTTP search request example
    //    https://api.openweathermap.org/data/2.5/weather?lat=33.44&lon=-94.04&appid={API key} - HTTP load request example

    public static LocationDTO getLocation(String locationName) {
        RestTemplate restTemplate = new RestTemplate();
        String url = GET_SEARCH_URL_FIRST_PART + locationName + GET_SEARCH_URL_SECOND_PART + API_KEY;
        String response = restTemplate.getForObject(url, String.class);

        try {
            return objectMapper.readValue(response,
                    new TypeReference<List<LocationDTO>>() {}).get(0);
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
}
