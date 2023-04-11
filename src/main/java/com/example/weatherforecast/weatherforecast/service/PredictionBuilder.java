package com.example.weatherforecast.weatherforecast.service;

import com.example.weatherforecast.weatherforecast.dto.WeatherDTO;
import com.example.weatherforecast.weatherforecast.model.Prediction;


public interface PredictionBuilder {

    void buildRainyPrediction(WeatherDTO weatherDTO);

    void buildWarmPrediction(WeatherDTO weatherDTO);

    void buildWindyPrediction(WeatherDTO weatherDTO);

    void buildThunderstormPrediction(WeatherDTO weatherDTO);

    Prediction getPrediction();
}
