package com.example.weatherforecast.weatherforecast.utility;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utility {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime convertStringLocalDate(String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        return dateTime;
    }
}
