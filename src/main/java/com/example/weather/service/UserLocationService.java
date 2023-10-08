package com.example.weather.service;

import com.example.weather.models.Location;
import com.example.weather.models.User;
import com.example.weather.models.UserLocation;
import com.example.weather.models.UserLocationKey;
import com.example.weather.repositories.UserLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class UserLocationService {

    private final UserLocationRepository userLocationRepository;

    @Autowired
    public UserLocationService(UserLocationRepository userLocationRepository) {
        this.userLocationRepository = userLocationRepository;
    }

    @Transactional
    public void save(User user, Location location) {
        UserLocationKey key = new UserLocationKey(user.getId(), location.getId());
        UserLocation userLocation = new UserLocation(key, user, location);
        userLocationRepository.save(userLocation);
    }

    @Transactional
    public Set<UserLocation> findUserLocation(User user) {
        return userLocationRepository.findUserLocation(user);
    }
}
