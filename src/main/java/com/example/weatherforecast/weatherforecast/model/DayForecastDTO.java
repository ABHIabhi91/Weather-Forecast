package com.example.weatherforecast.weatherforecast.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(force = true)
public class DayForecastDTO {

    private  List<DayForecast> dayForecasts;

}
