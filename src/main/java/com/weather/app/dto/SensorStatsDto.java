package com.weather.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SensorStatsDto {
    private String sensorName;
    private String metric;
    private Double min;
    private Double avg;
    private Double max;

}
