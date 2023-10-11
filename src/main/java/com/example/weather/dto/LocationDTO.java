package com.example.weather.dto;

import com.example.weather.models.entity.WeatherApiResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationDTO {

    @JsonProperty("lat")
    private BigDecimal lat;

    @JsonProperty("lon")
    private BigDecimal lon;

    @JsonProperty("name")
    private String name;

    private WeatherApiResponse weatherApiResponse;

    public LocationDTO(String name, WeatherApiResponse weatherApiResponse) {
        this.name = name;
        this.weatherApiResponse = weatherApiResponse;
    }
}
