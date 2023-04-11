package com.example.weatherforecast.weatherforecast.model;

import com.example.weatherforecast.weatherforecast.service.PredictionPlan;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
public class Prediction implements PredictionPlan {

    private  LocalDateTime timeDuration;

    private  String description;


    @Override
    public void setRainyPrediction(String rainyDescription, LocalDateTime localDateTime) {
       this.description = rainyDescription;
       this.timeDuration = localDateTime;
    }

    @Override
    public void setWarmPrediction(String warmDescription, LocalDateTime localDateTime) {
        this.description = warmDescription;
        this.timeDuration = localDateTime;
    }

    @Override
    public void setThunderstormPrediction(String thunderstormPrediction, LocalDateTime localDateTime) {
        this.description = thunderstormPrediction;
        this.timeDuration = localDateTime;
    }

    @Override
    public void setWindyPrediction(String windyPrediction, LocalDateTime localDateTime) {
        this.description = windyPrediction;
        this.timeDuration = localDateTime;
    }
}
