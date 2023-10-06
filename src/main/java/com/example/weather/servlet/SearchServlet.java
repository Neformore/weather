package com.example.weather.servlet;

import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SearchServlet {

    private static final String API_KEY = "a8fb3b67da5736ba5a1028e989bb34c2";
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String GET_URL_FIRST_PART = "http://api.openweathermap.org/geo/1.0/direct?q=";
    private static final String GET_URL_SECOND_PART = "&limit=1&appid=";
    //    http://api.openweathermap.org/geo/1.0/direct?q=London&limit=5&appid={API key} - HTTP request example

    public static String doGet(String locationName) {
        try {
            URL url = new URL(GET_URL_FIRST_PART + locationName + GET_URL_SECOND_PART + API_KEY);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", USER_AGENT);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String doRestGet(String locationName) {
        RestTemplate restTemplate = new RestTemplate();
        String url = GET_URL_FIRST_PART + locationName + GET_URL_SECOND_PART + API_KEY;
        return restTemplate.getForObject(url, String.class);
    }
}
