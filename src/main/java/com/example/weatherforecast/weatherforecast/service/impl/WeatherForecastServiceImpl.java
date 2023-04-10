package com.example.weatherforecast.weatherforecast.service.impl;

import com.example.weatherforecast.weatherforecast.dto.WeatherDTO;
import com.example.weatherforecast.weatherforecast.dto.WeatherForecastResponseDTO;
import com.example.weatherforecast.weatherforecast.model.DayForecast;
import com.example.weatherforecast.weatherforecast.model.DayForecastDTO;
import com.example.weatherforecast.weatherforecast.model.Prediction;
import com.example.weatherforecast.weatherforecast.service.RedisUtility;
import com.example.weatherforecast.weatherforecast.service.WeatherForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public DayForecastDTO getForecast(String city) {

        DayForecastDTO dayForecastDTO;

    //     dayForecastDTO = fetchFromRedis(city);

    //    if(dayForecastDTO == null) {

            dayForecastDTO = new DayForecastDTO();
            HttpHeaders headers = new HttpHeaders();


            HttpEntity<String> entity = new HttpEntity<String>(headers);


            String urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("q", city)
                    .queryParam("appid", appId)
                    .queryParam("cnt", 24)
                    .encode()
                    .toUriString();


            HttpEntity<WeatherForecastResponseDTO> responseEntity = restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, WeatherForecastResponseDTO.class);


            if (responseEntity != null && responseEntity.getBody() != null) {
                for (WeatherDTO weatherDTO : responseEntity.getBody().getList()) {
                    LocalDateTime localDate = convertStringLocalDate(weatherDTO.getDt_txt());
                    weatherDTO.setDate(localDate);
                }

                // create a map with key as date and value as list of that date

                LinkedHashMap<LocalDate, List<WeatherDTO>> collect = responseEntity.getBody().getList().stream().collect(Collectors.groupingBy(dto -> dto.getDate().toLocalDate(), LinkedHashMap::new, Collectors.toList()));


                List<DayForecast> dayForecastList = new ArrayList<>();
                for (Map.Entry<LocalDate, List<WeatherDTO>> entry : collect.entrySet()) {

                    DayForecast dayForecast = new DayForecast();
                    double tempMax = entry.getValue().stream().max(Comparator.comparingDouble(x -> x.getMain().getTemp_max())).get().getMain().getTemp_max();
                    double tempMin = entry.getValue().stream().min(Comparator.comparingDouble(x -> x.getMain().getTemp_min())).get().getMain().getTemp_min();
                    dayForecast.setHighTemp(tempMax);
                    dayForecast.setLowTemp(tempMin);
                    dayForecast.setDate(entry.getKey());


                    // convert Farheinite to Celcius and if greater than 40 degree or rainy season then mention
                    // rain is predicted in next 3 days or temperature goes above 40 degree Celsius then mention 'Carry umbrella' or 'Use sunscreen lotion' respectively in the output, for that day;
                    List<Prediction> predictionList = new ArrayList<>();
                    for (WeatherDTO weatherDto : entry.getValue()) {
                        double celcius = convertFahrenheitToCelcius(weatherDto);
                        if (celcius > 40.0 || weatherDto.getWeather().get(0).getMain().equalsIgnoreCase("Rain")) {
                            Prediction prediction = new Prediction();
                            prediction.setTimeDuration(weatherDto.getDate());
                            prediction.setDescription("Carry umbrella' or 'Use sunscreen lotion'");
                            predictionList.add(prediction);

                        } else if (weatherDto.getWind().getSpeed() > 10.0) {
                            Prediction prediction = new Prediction();
                            prediction.setTimeDuration(weatherDto.getDate());
                            prediction.setDescription("It’s too windy, watch out!");
                            predictionList.add(prediction);
                        } else if (weatherDto.getWeather().get(0).getMain().equalsIgnoreCase("Thunderstorm")) {
                            Prediction prediction = new Prediction();
                            prediction.setTimeDuration(weatherDto.getDate());
                            prediction.setDescription("Don’t step out! A Storm is brewing!");
                            predictionList.add(prediction);
                        }
                    }
                    dayForecast.setPredictions(predictionList);
                    dayForecastList.add(dayForecast);

                }

                dayForecastDTO.setDayForecasts(dayForecastList);


            }
       //     redisUtility.setValue(city+LocalDateTime.now(),dayForecastDTO);
   //     }

        return dayForecastDTO;

    }

    @Override
    public DayForecastDTO fetchFromRedis(String city) {
        DayForecastDTO dto = redisUtility.getValue(city+LocalDateTime.now());
        return  dto;
    }

    private Double convertFahrenheitToCelcius(WeatherDTO weatherDTO) {
        double celcius = (weatherDTO.getMain().getTemp_max() - 32) / 1.8;
        return celcius;
    }

    private LocalDateTime convertStringLocalDate(String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        return dateTime;
    }

}
