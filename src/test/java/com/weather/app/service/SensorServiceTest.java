package com.weather.app.service;

import com.weather.app.builder.SensorResponseBuilder;
import com.weather.app.dto.QueryRequest;
import com.weather.app.dto.QueryResponse;
import com.weather.app.entity.SensorData;
import com.weather.app.exception.BadRequestException;
import com.weather.app.repository.SensorDataRepository;
import com.weather.app.service.impl.SensorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SensorServiceTest {

    @Mock
    private SensorDataRepository repository;

    @Mock
    private SensorResponseBuilder responseBuilder;

    @InjectMocks
    private SensorServiceImpl service;

    @Test
    void shouldCalculateAverageTemperature() {
        // Mock DB result → Object[]
        Object[] row = new Object[]{"sensor-1", 25.0};

        List<Object[]> dbResult = List.of(new Object[][]{row});

        when(repository.getAggregatedSensorData(any())) .thenReturn(dbResult);

        QueryRequest request = new QueryRequest();
        request.setSensorNames(List.of("sensor-1"));
        request.setMetrics(List.of("temperature"));
        request.setStats(List.of("avg"));

        QueryResponse mockResponse = new QueryResponse(new ArrayList<>());

        when(responseBuilder.buildResponse(dbResult, request)) .thenReturn(mockResponse);

        QueryResponse result = service.querySensorData(request);

        // Assertions
        assertNotNull(result);

        // Verify interactions
        verify(repository, times(1)).getAggregatedSensorData(any());
        verify(responseBuilder, times(1)) .buildResponse(dbResult, request);
    }

    @Test
    void shouldHandleEmptyData() {
        when(repository.getAggregatedSensorData(any())).thenReturn(Collections.emptyList());

        QueryRequest request = new QueryRequest();
        request.setSensorNames(List.of("sensor-1"));
        request.setMetrics(List.of("temperature"));
        request.setStats(List.of("avg"));

        assertThrows(BadRequestException.class, () -> service.querySensorData(request));

        verify(repository, times(1)).getAggregatedSensorData(any());
        verify(responseBuilder, never()).buildResponse(any(), any());
    }
}