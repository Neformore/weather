package com.example.weather.repositories;

import com.example.weather.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
