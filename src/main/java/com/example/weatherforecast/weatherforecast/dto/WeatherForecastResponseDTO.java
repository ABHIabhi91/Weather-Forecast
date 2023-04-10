package com.example.weatherforecast.weatherforecast.dto;

import lombok.Data;

import java.util.List;

@Data
public class WeatherForecastResponseDTO {


    private int cnt;

    private List<WeatherDTO> list;
}
