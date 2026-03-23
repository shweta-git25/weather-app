package com.weather.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data /* creates boilerplate. No need of getters and setter */
@NoArgsConstructor
@AllArgsConstructor
public class SensorDataRequest {

    /* creating DTO for weather */
    private  String sensorName;
    private Double temperature;
    private Double humidity;
    private Double windSpeed;
    private LocalDateTime timestamp;

}
