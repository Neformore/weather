package com.example.weather.controllers;

import com.example.weather.dto.LocationDTO;
import com.example.weather.models.User;
import com.example.weather.servlet.SearchServlet;
import com.example.weather.util.JsonToLocation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/client")
public class UsersController {

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
    public String addLocation(@RequestParam BigDecimal lat, @RequestParam BigDecimal lon) {

        return "redirect:/client";
    }
}
