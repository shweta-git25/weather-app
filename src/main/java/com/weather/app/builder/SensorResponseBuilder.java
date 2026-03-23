package com.weather.app.builder;

import com.weather.app.dto.QueryRequest;
import com.weather.app.dto.QueryResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SensorResponseBuilder {

    public QueryResponse buildResponse(List<Object[]> results, QueryRequest request) {

        List<Map<String, Map<String, Map<String, Double>>>> finalResponse = new ArrayList<>();

        List<Map<String, Map<String, Map<String, Double>>>> sensorResults = buildSensorResults(results, request);

        finalResponse.addAll(sensorResults);


        return new QueryResponse(finalResponse);
    }

    private List<Map<String, Map<String, Map<String, Double>>>> buildSensorResults(List<Object[]> results, QueryRequest request) {

        List<Map<String, Map<String, Map<String, Double>>>> sensorResults = new ArrayList<>();

        for (Object[] row : results) {
            sensorResults.add(buildSingleSensorResponse(row, request));
        }

        return sensorResults;
    }

    private Map<String, Map<String, Map<String, Double>>> buildSingleSensorResponse( Object[] row, QueryRequest request) {

        String sensorName = (String) row[0];

        Map<String, Map<String, Double>> metricMap = new HashMap<>();

        int index = 1;

        for (String metric : request.getMetrics()) {

            Map<String, Double> statMap = new HashMap<>();

            for (String stat : request.getStats()) {
                statMap.put(stat, extractValue(row[index++]));
            }

            metricMap.put(metric, statMap);
        }

        Map<String, Map<String, Map<String, Double>>> wrapper = new HashMap<>();
        wrapper.put(sensorName, metricMap);

        return wrapper;
    }

    private double extractValue(Object value) {
        return value == null ? 0.0 : ((Number) value).doubleValue();
    }

}
