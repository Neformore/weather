package com.example.weather.controllers;

import com.example.weather.dto.LocationDTO;
import com.example.weather.models.Location;
import com.example.weather.models.User;
import com.example.weather.secutiry.UsersDetails;
import com.example.weather.service.LocationService;
import com.example.weather.service.UsersService;
import com.example.weather.servlet.SearchServlet;
import com.example.weather.util.JsonToLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/client")
public class UsersController {

    private final LocationService locationService;
    private final UsersService usersService;

    @Autowired
    public UsersController(LocationService locationService, UsersService usersService) {
        this.locationService = locationService;
        this.usersService = usersService;
    }

    @GetMapping()
    public String getClientPage(@ModelAttribute("user") User user) {
        return "client-page";
    }

    @GetMapping("/search-result")
    public String getSearchResultPage(@ModelAttribute("locationDTO") LocationDTO locationDTO) {
        return "search-result";
    }

    @PostMapping("/find")
    public String findLocation(@RequestParam String locationName, Model model) {
        model.addAttribute("locationDTO", JsonToLocation.makeLocationFromJson(SearchServlet.doRestGet(locationName)));
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
            locationService.addUser(user, location);
            usersService.addLocation(user, location);
            locationService.save(location);
        } else {
            locationService.addUser(user, opLocation.get());
            usersService.addLocation(user, opLocation.get());
        }
        return "redirect:/client";
    }
}
