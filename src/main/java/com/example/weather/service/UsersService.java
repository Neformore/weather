package com.example.weather.service;

import com.example.weather.models.Location;
import com.example.weather.models.User;
import com.example.weather.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Optional<User> findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    @Transactional
    public void addLocation(User user, Location location) {
        if (user.getLocations() == null) {
            user.setLocations(new HashSet<>(Set.of(location)));
        } else {
            user.getLocations().add(location);
        }
    }
}
