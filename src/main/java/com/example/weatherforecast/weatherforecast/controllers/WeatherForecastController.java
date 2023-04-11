package com.example.weatherforecast.weatherforecast.controllers;

import com.example.weatherforecast.weatherforecast.model.DayForecastDTO;
import com.example.weatherforecast.weatherforecast.service.WeatherForecastService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController()
@RequestMapping(value = "v1/weather-forecast", produces = MediaType.APPLICATION_JSON_VALUE)
public class WeatherForecastController {

    @Autowired
    private WeatherForecastService weatherForecastService;

    @GetMapping(path="/{city}")
    public ResponseEntity<DayForecastDTO> getWeatherStatus(@PathVariable("city") String city ){

        DayForecastDTO dayForecastDTO = weatherForecastService.getWeatherInfo(city);

        return ResponseEntity.ok(dayForecastDTO);
    }

}
