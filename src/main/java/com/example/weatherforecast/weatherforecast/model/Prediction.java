package com.example.weatherforecast.weatherforecast.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Prediction {

    private  LocalDateTime timeDuration;

    private  String description;
}
