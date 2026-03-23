package com.weather.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_sensor_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SensorData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long sensorId;

    @Column(name = "sensor_name")
    private  String sensorName;

    @Column(name = "temperature")
    private Double temperature;

    @Column(name = "humidity")
    private Double humidity;

    @Column(name = "wind_speed")
    private Double windSpeed;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;


    @Override
    public String toString() {
        return "SensorData{" +
                "sensorId=" + sensorId +
                ", sensorName='" + sensorName + '\'' +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", windSpeed=" + windSpeed +
                ", timestamp=" + timestamp +
                '}';
    }
}
