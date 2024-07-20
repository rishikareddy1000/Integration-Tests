package com.test.integration_tests.resources.pojos;

import lombok.Data;
import lombok.Getter;

import java.util.Map;

@Getter
@Data
public class GeocodingAPIResponse {
    private Map<String, String> local_names;
    private String name;
    private double lat;
    private double lon;
    private String country;
    private String state;
}
