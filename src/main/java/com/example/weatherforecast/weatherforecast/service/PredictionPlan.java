package com.example.weatherforecast.weatherforecast.service;

import java.time.LocalDateTime;

public interface PredictionPlan {

    void setRainyPrediction(String rainyDescription, LocalDateTime localDateTime);

    void setWarmPrediction(String warmDescription, LocalDateTime localDateTime);

    void setThunderstormPrediction(String thunderstormPrediction, LocalDateTime localDateTime);

    void setWindyPrediction(String windyPrediction, LocalDateTime localDateTime);

}
