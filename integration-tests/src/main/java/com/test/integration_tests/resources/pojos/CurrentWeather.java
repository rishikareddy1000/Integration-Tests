package com.test.integration_tests.resources.pojos;

import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.Map;
@Data
public class CurrentWeather {
    private long dt;
    private long sunrise;
    private long sunset;
    private double temp;
    private double feels_like;
    private int pressure;
    private int humidity;
    private double dew_point;
    private double uvi;
    private int clouds;
    private int visibility;
    private double wind_speed;
    private int wind_deg;
    private double wind_gust;
    private List<Weather> weather;

}
