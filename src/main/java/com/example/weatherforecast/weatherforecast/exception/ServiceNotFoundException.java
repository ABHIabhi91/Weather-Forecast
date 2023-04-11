package com.example.weatherforecast.weatherforecast.exception;

public class ServiceNotFoundException  extends RuntimeException {

    public ServiceNotFoundException(String msg) {
        super(String.format("Service is down kindly try after sometime"));
    }

}
