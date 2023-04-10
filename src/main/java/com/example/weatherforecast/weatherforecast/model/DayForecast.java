package com.example.weatherforecast.weatherforecast.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor(force = true)
public class DayForecast {

private  LocalDate date;
private  Double highTemp;
private  Double lowTemp;
private  List<Prediction> predictions;


}
