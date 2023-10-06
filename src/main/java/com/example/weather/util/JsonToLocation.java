package com.example.weather.util;

import com.example.weather.dto.LocationDTO;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

public class JsonToLocation {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static LocationDTO makeLocationFromJson(String stringJson) {
        String latAndLonValues = stringJson.substring(stringJson.indexOf("lat"), stringJson.indexOf("country") - 2);
        String locationName = stringJson.substring(stringJson.indexOf(":") + 2, stringJson.indexOf(",") - 1);
        BigDecimal latValue = new BigDecimal(latAndLonValues.substring(5, latAndLonValues.indexOf(",")));
        BigDecimal lonValue = new BigDecimal(latAndLonValues.substring(latAndLonValues.indexOf("lon") + 5));
        return new LocationDTO(latValue, lonValue, locationName);
    }
}
