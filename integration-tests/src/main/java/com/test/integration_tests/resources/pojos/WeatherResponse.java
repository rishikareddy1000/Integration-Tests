package com.test.integration_tests.resources.pojos;

import lombok.Data;

@Data
public class WeatherResponse {
    private double lat;
    private double lon;
    private String timezone;
    private int timezone_offset;
    private CurrentWeather current;
}
