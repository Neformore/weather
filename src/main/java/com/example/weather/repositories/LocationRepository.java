package com.example.weather.repositories;

import com.example.weather.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
    Optional<Location> findByName(String name);

    void deleteById(int id);
}
