package com.example.weather.repositories;

import com.example.weather.models.Location;
import com.example.weather.models.User;
import com.example.weather.models.UserLocation;
import com.example.weather.models.UserLocationKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserLocationRepository extends JpaRepository<UserLocation, Integer> {

    @Query("select u from UserLocation u where u.user = ?1")
    Set<UserLocation> findUserLocation(User user);

    void deleteUserLocationById(UserLocationKey userLocationKey);

    Optional<UserLocation> findUserLocationByLocation(Location location);
}
