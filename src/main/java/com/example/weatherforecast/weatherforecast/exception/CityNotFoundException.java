package com.example.weatherforecast.weatherforecast.exception;

public class CityNotFoundException extends RuntimeException {

    public CityNotFoundException(String city) {
        super(String.format("City with this name %s not found", city));
    }

}
