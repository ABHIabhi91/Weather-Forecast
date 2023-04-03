package com.example.weatherforecast.weatherforecast.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController()
@RequestMapping(value = "/weather-forecast", produces = MediaType.APPLICATION_JSON_VALUE)
public class WeatherForecastController {

    @GetMapping(path="/{city}")
    public String getWeatherStatus(@PathVariable("city") String city ){
        return "welcome to " + city;
    }

}
