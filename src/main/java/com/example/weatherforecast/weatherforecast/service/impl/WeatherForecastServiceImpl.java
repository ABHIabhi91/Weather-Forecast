package com.example.weatherforecast.weatherforecast.service.impl;

import com.example.weatherforecast.weatherforecast.builder.PredicateCreationalBuilder;
import com.example.weatherforecast.weatherforecast.dto.WeatherDTO;
import com.example.weatherforecast.weatherforecast.dto.WeatherForecastResponseDTO;
import com.example.weatherforecast.weatherforecast.exception.CityNotFoundException;
import com.example.weatherforecast.weatherforecast.model.DayForecast;
import com.example.weatherforecast.weatherforecast.model.DayForecastDTO;
import com.example.weatherforecast.weatherforecast.model.Prediction;
import com.example.weatherforecast.weatherforecast.service.FetchWeatherService;
import com.example.weatherforecast.weatherforecast.service.PredictionBuilder;
import com.example.weatherforecast.weatherforecast.service.RedisUtility;
import com.example.weatherforecast.weatherforecast.service.WeatherForecastService;
import com.example.weatherforecast.weatherforecast.utility.Utility;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WeatherForecastServiceImpl implements WeatherForecastService {

    @Value("${url}")
    private String url;

    @Value("${appid}")
    private String appId;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisUtility redisUtility;

    @Autowired
    private FetchWeatherService fetchWeatherService;

    @Override
    public DayForecastDTO getWeatherInfo(String city) {

        DayForecastDTO dayForecastDTO;

        dayForecastDTO = fetchFromRedis(city);

        if (dayForecastDTO == null) {

            dayForecastDTO = new DayForecastDTO();


            HttpEntity<WeatherForecastResponseDTO> responseEntity = fetchWeatherService.fetchFromAPI(city);

            if (responseEntity != null && responseEntity.getBody() != null) {
                for (WeatherDTO weatherDTO : responseEntity.getBody().getList()) {
                    LocalDateTime localDate = Utility.convertStringLocalDate(weatherDTO.getDt_txt());
                    weatherDTO.setDate(localDate);
                }

                // create a map with key as date and value as list of that date

                LinkedHashMap<LocalDate, List<WeatherDTO>> collect = responseEntity.getBody().getList().stream().collect(Collectors.groupingBy(dto -> dto.getDate().toLocalDate(), LinkedHashMap::new, Collectors.toList()));


                List<DayForecast> dayForecastList = new ArrayList<>();
                for (Map.Entry<LocalDate, List<WeatherDTO>> entry : collect.entrySet()) {

                    DayForecast dayForecast = new DayForecast();
                    double tempMax = entry.getValue().stream().max(Comparator.comparingDouble(x -> x.getMain().getTemp_max())).get().getMain().getTemp_max();
                    double tempMin = entry.getValue().stream().min(Comparator.comparingDouble(x -> x.getMain().getTemp_min())).get().getMain().getTemp_min();
                    dayForecast.setHighTemp(tempMax - 273);
                    dayForecast.setLowTemp(tempMin - 273);
                    dayForecast.setDate(entry.getKey());


                    // convert Farheinite to Celcius and if greater than 40 degree or rainy season then mention
                    // rain is predicted in next 3 days or temperature goes above 40 degree Celsius then mention 'Carry umbrella' or 'Use sunscreen lotion' respectively in the output, for that day;
                    List<Prediction> predictionList = getPredictions(entry);
                    dayForecast.setPredictions(predictionList);
                    dayForecastList.add(dayForecast);

                }

                dayForecastDTO.setDayForecasts(dayForecastList);


            }
            redisUtility.setValue(city + LocalDate.now(), dayForecastDTO);

        }

        return dayForecastDTO;

    }

    private List<Prediction> getPredictions(Map.Entry<LocalDate, List<WeatherDTO>> entry) {
        List<Prediction> predictionList = new ArrayList<>();
        for (WeatherDTO weatherDto : entry.getValue()) {
            PredictionBuilder predictionBuilder = new PredictionObjectBuilder();
            PredicateCreationalBuilder builder = new PredicateCreationalBuilder(predictionBuilder);

            builder.constructPrediction(weatherDto);
            Prediction prediction = builder.getPrediction();

            if (prediction.getDescription() != null) {
                predictionList.add(prediction);
            }

        }
        return predictionList;
    }

    @Override
    public DayForecastDTO fetchFromRedis(String city) {
        DayForecastDTO dto = redisUtility.getValue(city + LocalDate.now());
        return dto;
    }


    public HttpEntity<WeatherForecastResponseDTO> fetchFromAPI(String city) {

        HttpHeaders headers = new HttpHeaders();

        ResponseEntity<WeatherForecastResponseDTO> responseEntity = null;

        HttpEntity<String> entity = new HttpEntity<String>(headers);


        // try {

        String urlTemplate = UriComponentsBuilder.fromHttpUrl("http://localhost:8000/api.openweathermap.org/data/2.5/forecast")
                .queryParam("q", city)
                .queryParam("appid", appId)
                .queryParam("cnt", 24)
                .encode()
                .toUriString();


        responseEntity = restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, WeatherForecastResponseDTO.class);

//        }  catch (HttpClientErrorException | HttpServerErrorException e ) {
//            throw new CityNotFoundException(city);
//        }
        return responseEntity;
    }

//    public String  getAPIFallBack(String topicPage, Exception e){
//        System.out.println("getAPIFallBack::{}" + e.getMessage() +topicPage);
//
//        return topicPage;
//    }

}
