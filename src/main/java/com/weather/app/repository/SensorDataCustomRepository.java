package com.weather.app.repository;

import com.weather.app.dto.QueryRequest;

import java.util.List;

public interface SensorDataCustomRepository {

    List<Object[]> getAggregatedSensorData(QueryRequest request);
}
