package com.example.weather.servlet;

import com.example.weather.models.Location;
import com.example.weather.models.UserLocation;
import org.springframework.web.client.RestTemplate;

public class SearchServlet {

    private static final String API_KEY = "a8fb3b67da5736ba5a1028e989bb34c2";
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String GET_SEARCH_URL_FIRST_PART = "http://api.openweathermap.org/geo/1.0/direct?q=";
    private static final String GET_SEARCH_URL_SECOND_PART = "&limit=1&appid=";
    private static final String GET_LOAD_URL_FIRST_PART = "https://api.openweathermap.org/data/2.5/weather?";

    //    http://api.openweathermap.org/geo/1.0/direct?q=London&limit=5&appid={API key} - HTTP search request example
    //    https://api.openweathermap.org/data/2.5/weather?lat=33.44&lon=-94.04&appid={API key} - HTTP load request example

    public static String doRestSearchGet(String locationName) {
        RestTemplate restTemplate = new RestTemplate();
        String url = GET_SEARCH_URL_FIRST_PART + locationName + GET_SEARCH_URL_SECOND_PART + API_KEY;
        return restTemplate.getForObject(url, String.class);
    }

    public static String doLoadLocationGet(UserLocation userLocation) {
        RestTemplate restTemplate = new RestTemplate();
        Location location = userLocation.getLocation();
        String url = GET_LOAD_URL_FIRST_PART + "lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&appid=" + API_KEY;
        String response = restTemplate.getForObject(url, String.class);
        return null;
    }
}
