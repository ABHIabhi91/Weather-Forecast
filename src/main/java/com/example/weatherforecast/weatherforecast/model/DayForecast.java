package com.example.weatherforecast.weatherforecast.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor(force = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DayForecast {

private  LocalDate date;
private  Double highTemp;
private  Double lowTemp;
private  List<Prediction> predictions;


}
