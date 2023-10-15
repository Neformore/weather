package com.example.weather.controllers;

import com.example.weather.dto.LocationDTO;
import com.example.weather.models.Location;
import com.example.weather.models.User;
import com.example.weather.models.UserLocation;
import com.example.weather.secutiry.UsersDetails;
import com.example.weather.service.LocationService;
import com.example.weather.service.UserLocationService;
import com.example.weather.service.WeatherApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/client")
public class UsersController {

    private final LocationService locationService;
    private final UserLocationService userLocationService;

    @Autowired
    public UsersController(LocationService locationService, UserLocationService userLocationService) {
        this.locationService = locationService;
        this.userLocationService = userLocationService;
    }

    @GetMapping()
    public String getClientPage(@AuthenticationPrincipal UsersDetails usersDetails, Model model) {
        User user = usersDetails.getUser();
        user.setUserLocation(userLocationService.findUserLocation(user));
        List<LocationDTO> userWeather = new ArrayList<>();

        for (UserLocation userLocation : user.getUserLocation()) {
            userWeather.add(new LocationDTO(userLocation.getLocation().getName(), WeatherApiService.getWeather(userLocation)));
        }

        model.addAttribute("user", user);
        model.addAttribute("weatherList", userWeather);
        return "client-page";
    }

    @GetMapping("/search-result")
    public String getSearchResultPage(@ModelAttribute("locationDTO") LocationDTO locationDTO,
                                      @AuthenticationPrincipal UsersDetails usersDetails,
                                      Model model) {
        model.addAttribute("user", usersDetails.getUser());
        return "search-result";
    }

    @PostMapping("/find")
    public String findLocation(@RequestParam String locationName,
                               @AuthenticationPrincipal UsersDetails usersDetails,
                               Model model) {
        model.addAttribute("locationDTO", WeatherApiService.getLocation(locationName));
        model.addAttribute("user", usersDetails.getUser());
        return "search-result";
    }

    @PostMapping("/add-location")
    public String addLocation(@AuthenticationPrincipal UsersDetails usersDetails,
                              @RequestParam BigDecimal lat,
                              @RequestParam BigDecimal lon,
                              @RequestParam String locationName) {
        User user = usersDetails.getUser();
        Optional<Location> opLocation = locationService.findByName(locationName);

        if (opLocation.isEmpty()) {
            Location location = new Location(lat, lon, locationName);
            locationService.save(location);
            userLocationService.save(user, location);
        } else {
            userLocationService.save(user, opLocation.get());
        }
        return "redirect:/client";
    }

    @PostMapping("/forecast/{locationName}")
    public String forecast(@PathVariable("locationName") String locationName,
                           @AuthenticationPrincipal UsersDetails usersDetails,
                           Model model) {
        Optional<UserLocation> userLocationOptional = getUserLocation(locationName, usersDetails);
        model.addAttribute("forecast", WeatherApiService.getForecast(userLocationOptional.get()).getHourlyForecasts());
        model.addAttribute("user", usersDetails.getUser());
        return "forecast";
    }

    @DeleteMapping("/{locationName}")
    public String removeLocation(@PathVariable("locationName") String locationName,
                                 @AuthenticationPrincipal UsersDetails usersDetails) {

        Optional<UserLocation> userLocationOptional = getUserLocation(locationName, usersDetails);

        userLocationService.remove(userLocationOptional.get().getId());

        if (userLocationService.findByLocation(userLocationOptional.get().getLocation()).isEmpty()) {
            locationService.remove(userLocationOptional.get().getLocation());
        }

        return "redirect:/client";
    }

    private Optional<UserLocation> getUserLocation(String locationName, UsersDetails usersDetails) {
        return usersDetails.getUser().getUserLocation().stream()
                .filter(userLocation -> userLocation
                        .getLocation()
                        .getName()
                        .equals(locationName))
                .findAny();
    }
}
