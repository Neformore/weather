package com.example.weather.service;

import com.example.weather.models.Location;
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

    public Optional<Location> findByName(String name) {
        return locationRepository.findByName(name);
    }

    @Transactional
    public void save(Location location) {
        locationRepository.save(location);
    }

    @Transactional
    public void remove(Location location) {
        locationRepository.deleteById(location.getId());
    }

}
