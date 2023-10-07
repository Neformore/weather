package com.example.weather.service;

import com.example.weather.models.Location;
import com.example.weather.models.User;
import com.example.weather.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Transactional
    public void save(Location location) {
        locationRepository.save(location);
    }

    public Optional<Location> findByName(String name) {
        return locationRepository.findByName(name);
    }

    public void addUser(User user, Location location) {
        if(location.getUsers() == null) {
            location.setUsers(new HashSet<>(Set.of(user)));
        } else {
            location.getUsers().add(user);
        }
    }
}
