package com.example.weather.models.entity;

import com.example.weather.util.LocalDateTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastApiResponse {

    @JsonProperty("list")
    private List<HourlyForecast> hourlyForecasts;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HourlyForecast {

        @JsonProperty("dt")
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime dateTime;

        @JsonProperty("main")
        private Main main;

        @JsonProperty("wind")
        private Wind wind;

        @JsonProperty("weather")
        private List<Weather> weather;
    }
}
