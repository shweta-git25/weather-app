package com.weather.app.service.impl;

import com.weather.app.builder.SensorResponseBuilder;
import com.weather.app.dto.QueryRequest;
import com.weather.app.dto.QueryResponse;
import com.weather.app.dto.SensorDataRequest;
import com.weather.app.entity.SensorData;
import com.weather.app.exception.BadRequestException;
import com.weather.app.exception.ResourceNotFoundException;
import com.weather.app.repository.SensorDataRepository;
import com.weather.app.service.SensorService;
import com.weather.app.validation.QueryRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SensorServiceImpl implements SensorService {

    private final SensorDataRepository sensorDataRepository;
    private final SensorResponseBuilder responseBuilder;

    @Override
    public void saveAllSensorData(List<SensorDataRequest> requests) {
        List<SensorData> dataList = requests.stream().map(req -> {
            SensorData data = new SensorData();
            data.setSensorName(req.getSensorName());
            data.setTemperature(req.getTemperature());
            data.setHumidity(req.getHumidity());
            data.setWindSpeed(req.getWindSpeed());
            data.setTimestamp(req.getTimestamp() != null
                            ? req.getTimestamp()
                            : java.time.LocalDateTime.now().withNano(0)
            );
            return data;
        }).toList();

        sensorDataRepository.saveAll(dataList);
    }

    @Override
    public QueryResponse querySensorData(QueryRequest request) {
        QueryRequestValidator.validate(request);

        List<Object[]> results = sensorDataRepository.getAggregatedSensorData(request);

        if (results.isEmpty()) {
            throw new BadRequestException("No data found");
        }

        return responseBuilder.buildResponse(results, request);
    }


    @Override
    public List<SensorData> getAllSensorData() {
        List<SensorData> data = sensorDataRepository.findAll();
        return data.stream()
                .map(d -> new SensorData(
                        d.getSensorId(),
                        d.getSensorName(),
                        d.getTemperature(),
                        d.getHumidity(),
                        d.getWindSpeed(),
                        d.getTimestamp()
                ))
                .toList();
    }

    @Override
    public List<SensorData> getSensorsByName(String sensorName) {

        List<SensorData> data = sensorDataRepository.findBySensorName(sensorName);

        if (data.isEmpty()) {
            throw new ResourceNotFoundException( "No data found for sensor: " + sensorName);
        }

        return data;
    }

}
