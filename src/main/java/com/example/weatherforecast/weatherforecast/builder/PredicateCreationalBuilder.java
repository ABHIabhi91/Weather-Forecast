package com.example.weatherforecast.weatherforecast.builder;

import com.example.weatherforecast.weatherforecast.dto.WeatherDTO;
import com.example.weatherforecast.weatherforecast.model.Prediction;
import com.example.weatherforecast.weatherforecast.service.PredictionBuilder;


public class PredicateCreationalBuilder {

    private PredictionBuilder predictionBuilder;

    public PredicateCreationalBuilder(PredictionBuilder predictionBuilder) {
        this.predictionBuilder = predictionBuilder;
    }

    public Prediction getPrediction() {
        return this.predictionBuilder.getPrediction();
    }


    public void constructPrediction(WeatherDTO weatherDTO) {
        this.predictionBuilder.buildRainyPrediction(weatherDTO);

        this.predictionBuilder.buildWarmPrediction(weatherDTO);

        this.predictionBuilder.buildWindyPrediction(weatherDTO);

        this.predictionBuilder.buildThunderstormPrediction(weatherDTO);
    }


}
