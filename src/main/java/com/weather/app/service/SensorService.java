package com.weather.app.service;

import com.weather.app.dto.QueryRequest;
import com.weather.app.dto.QueryResponse;
import com.weather.app.dto.SensorDataRequest;
import com.weather.app.entity.SensorData;

import java.util.List;

public interface SensorService {

    void saveAllSensorData(List<SensorDataRequest> request);

    QueryResponse querySensorData(QueryRequest request);

    List<SensorData> getAllSensorData();

    List<SensorData> getSensorsByName(String sensorName);
}
