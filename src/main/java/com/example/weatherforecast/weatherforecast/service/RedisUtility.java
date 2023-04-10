package com.example.weatherforecast.weatherforecast.service;

import com.example.weatherforecast.weatherforecast.model.DayForecastDTO;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisUtility {

    @Qualifier("redisTemplate")
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    Gson gson;

    public void setValue(final String key, DayForecastDTO dayForecastDTO) {

        redisTemplate.opsForValue().set(key, gson.toJson(dayForecastDTO));

        redisTemplate.expire(key, 10, TimeUnit.MINUTES);
    }

    public DayForecastDTO getValue(final String key) {

        DayForecastDTO day = gson.fromJson(redisTemplate.opsForValue().get(key), DayForecastDTO.class);
        System.out.println("data ...."+day);
        return day;
    }

    public void deleteKeyFromRedis(String key) {
        redisTemplate.delete(key);
    }


}
