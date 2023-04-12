package com.example.weatherforecast.weatherforecast.service.impl;

import com.example.weatherforecast.weatherforecast.dto.WeatherForecastResponseDTO;
import com.example.weatherforecast.weatherforecast.exception.CityNotFoundException;
import com.example.weatherforecast.weatherforecast.exception.ServiceNotFoundException;
import com.example.weatherforecast.weatherforecast.model.DayForecastDTO;
import com.example.weatherforecast.weatherforecast.service.FetchWeatherService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Base64;

@Service

public class FetchWeatherServiceImpl implements FetchWeatherService {

    @Value("${url}")
    private String url;

    @Value("${appid}")
    private String appId;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @CircuitBreaker(name = "myProjectAllRemoteCallsCB", fallbackMethod = "getAPIFallBack")
    public HttpEntity<WeatherForecastResponseDTO> fetchFromAPI(String city) {
        HttpHeaders headers = new HttpHeaders();

        ResponseEntity<WeatherForecastResponseDTO> responseEntity = null;

        HttpEntity<String> entity = new HttpEntity<String>(headers);


        String urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("q", city)
                .queryParam("appid", decodeString(appId))
                .queryParam("cnt", 24)
                .encode()
                .toUriString();


        responseEntity = restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, WeatherForecastResponseDTO.class);


        return responseEntity;
    }

    public ResponseEntity<DayForecastDTO> getAPIFallBack(String city, Throwable e) {
        if (e.getMessage().contains("city not found")) {
            throw new CityNotFoundException(city);
        } else {
            throw new ServiceNotFoundException("Service is down kindly retry after some time");
        }
    }

    private String decodeString(String encodedString) {

        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        String decodedString = new String(decodedBytes);
        return decodedString;
    }
}
