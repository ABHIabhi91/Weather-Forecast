package com.example.weatherforecast.weatherforecast.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<Object> handleCityNotFoundException( CityNotFoundException ex, WebRequest request) {

       Map<String ,Object> body = new LinkedHashMap<>();
       body.put("timestamp", LocalDateTime.now());
       body.put("message","City Not Found");

       return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ServiceNotFoundException.class)
    public ResponseEntity<Object> handleServiceDownException( ServiceNotFoundException ex, WebRequest request) {

        Map<String ,Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message","Service is down kindly try after sometime");

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
