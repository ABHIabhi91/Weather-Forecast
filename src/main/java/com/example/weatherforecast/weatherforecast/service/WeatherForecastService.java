package com.example.weatherforecast.weatherforecast.service;

import com.example.weatherforecast.weatherforecast.model.DayForecastDTO;

public interface WeatherForecastService {

    DayForecastDTO getWeatherInfo(String city);

    DayForecastDTO fetchFromRedis(String city);
}
