package com.weather.app.dto;

import lombok.Data;

import java.util.List;

@Data
public class QueryRequest {

    private List<String> sensorNames;   // optional
    private List<String> metrics;       // required
    private List<String> stats;         // avg, min, max, sum

    private String startDate;    // optional
    private String endDate;      // optional
}