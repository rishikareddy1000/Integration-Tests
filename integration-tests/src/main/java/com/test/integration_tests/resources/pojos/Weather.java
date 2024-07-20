package com.test.integration_tests.resources.pojos;

import lombok.Data;

@Data
public class Weather {
    private int id;
    private String main;
    private String description;
    private String icon;
}
