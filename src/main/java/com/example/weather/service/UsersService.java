package com.example.weather.service;

import com.example.weather.models.User;
import com.example.weather.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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


}
