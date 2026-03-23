package com.weather.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class QueryResponse {
    private List<Map<String, Map<String, Map<String, Double>>>> responseData;
}
