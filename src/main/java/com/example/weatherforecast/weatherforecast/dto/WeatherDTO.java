package com.example.weatherforecast.weatherforecast.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class WeatherDTO {

    private TempDetails main;

    private List<WeatherDetails> weather;

    private WindDetails wind;

    private String dt_txt;

    private LocalDateTime date;

}
