package com.example.weatherforecast.weatherforecast.service;

import com.example.weatherforecast.weatherforecast.dto.WeatherForecastResponseDTO;
import org.springframework.http.HttpEntity;

public interface FetchWeatherService {

    HttpEntity<WeatherForecastResponseDTO> fetchFromAPI(String city);

}
