package com.example.weatherforecast.weatherforecast.service;

import com.example.weatherforecast.weatherforecast.model.DayForecastDTO;

public interface WeatherForecastService {

    DayForecastDTO getForecast(String city);

    DayForecastDTO fetchFromRedis(String city);
}
