package com.weather.app.repository;

import com.weather.app.entity.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorDataRepository extends JpaRepository<SensorData, Long>, SensorDataCustomRepository {

    List<SensorData> findBySensorName(String sensorName);



}
