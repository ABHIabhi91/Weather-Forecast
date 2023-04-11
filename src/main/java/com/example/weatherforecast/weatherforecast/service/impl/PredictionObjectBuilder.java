package com.example.weatherforecast.weatherforecast.service.impl;

import com.example.weatherforecast.weatherforecast.dto.WeatherDTO;
import com.example.weatherforecast.weatherforecast.model.Prediction;
import com.example.weatherforecast.weatherforecast.service.PredictionBuilder;


public class PredictionObjectBuilder implements PredictionBuilder {

    private Prediction prediction;

    public PredictionObjectBuilder() {
        this.prediction = new Prediction();
    }


    // DTO input
    @Override
    public void buildRainyPrediction(WeatherDTO weatherDTO) {
        if (weatherDTO.getWeather().get(0).getMain().equalsIgnoreCase("Rain")) {
            prediction.setRainyPrediction("Carry umbrella", weatherDTO.getDate());
        }
    }

    @Override
    public void buildWarmPrediction(WeatherDTO weatherDTO) {
        if (weatherDTO.getMain().getTemp_max() - 273 > 40.0) {
            prediction.setWarmPrediction("Carry umbrella' or 'Use sunscreen lotion'", weatherDTO.getDate());
        }
    }

    @Override
    public void buildWindyPrediction(WeatherDTO weatherDTO) {
        if (weatherDTO.getWind().getSpeed() > 10.0) {
            prediction.setWindyPrediction("It’s too windy, watch out!", weatherDTO.getDate());

        }
    }


    @Override
    public void buildThunderstormPrediction(WeatherDTO weatherDTO) {
        if (weatherDTO.getWeather().get(0).getMain().equalsIgnoreCase("Thunderstorm")) {
            prediction.setThunderstormPrediction("Don’t step out! A Storm is brewing!", weatherDTO.getDate());
        }
    }

    @Override
    public Prediction getPrediction() {
        return this.prediction;
    }


}
