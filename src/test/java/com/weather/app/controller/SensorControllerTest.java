package com.weather.app.controller;

import com.weather.app.dto.QueryRequest;
import com.weather.app.dto.QueryResponse;
import com.weather.app.service.SensorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SensorControllerTest {

    @InjectMocks
    private SensorController controller;

    @Mock
    private SensorService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturn200ForValidQuery() {

        QueryRequest request = new QueryRequest();
        request.setSensorNames(List.of("sensor-1"));
        request.setMetrics(List.of("temperature"));
        request.setStats(List.of("avg"));

        // Mock response structure
        Map<String, Double> statMap = new HashMap<>();
        statMap.put("avg", 25.0);

        Map<String, Map<String, Double>> metricMap = new HashMap<>();
        metricMap.put("temperature", statMap);

        Map<String, Map<String, Map<String, Double>>> sensorMap = new HashMap<>();
        sensorMap.put("sensor-1", metricMap);

        List<Map<String, Map<String, Map<String, Double>>>> responseList = List.of(sensorMap);

        QueryResponse mockResponse = new QueryResponse(responseList);

        when(service.querySensorData(request)).thenReturn(mockResponse);

        ResponseEntity<?> response = controller.querySensorData(request);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        verify(service, times(1)).querySensorData(request);
    }

    @Test
    void shouldHandleException() {

        QueryRequest request = new QueryRequest();
        request.setSensorNames(List.of("sensor-1"));
        request.setMetrics(List.of("temperature"));
        request.setStats(List.of("avg"));

        when(service.querySensorData(request)).thenThrow(new RuntimeException("Error"));

        assertThrows(RuntimeException.class, () -> {
            controller.querySensorData(request);
        });
    }
}