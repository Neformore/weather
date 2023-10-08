package com.example.weather.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class UserLocationKey implements Serializable {

    @Column(name = "user_id")
    private int userId;

    @Column(name = "location_id")
    private int locationId;
}
