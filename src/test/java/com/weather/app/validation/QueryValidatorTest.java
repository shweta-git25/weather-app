package com.weather.app.validation;

import com.weather.app.dto.QueryRequest;
import com.weather.app.exception.BadRequestException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QueryValidatorTest {

    @Test
    void shouldPassValidRequest() {
        QueryRequest request = new QueryRequest();
        request.setMetrics(List.of("temperature"));
        request.setStats(List.of("avg"));
        request.setStartDate("20260301");
        request.setEndDate("20260310");

        assertDoesNotThrow(() -> QueryRequestValidator.validate(request));
    }

    @Test
    void shouldFailWhenMetricsMissing() {
        QueryRequest request = new QueryRequest();
        request.setStats(List.of("avg"));

        assertThrows(BadRequestException.class, () -> QueryRequestValidator.validate(request));
    }

    @Test
    void shouldFailWhenDateRangeExceeds31Days() {
        QueryRequest request = new QueryRequest();
        request.setMetrics(List.of("temperature"));
        request.setStats(List.of("avg"));
        request.setStartDate("20260101");
        request.setEndDate("20260310"); // > 31 days

        assertThrows(BadRequestException.class, () -> QueryRequestValidator.validate(request));
    }

    @Test
    void shouldFailWhenStartAfterEnd() {
        QueryRequest request = new QueryRequest();
        request.setMetrics(List.of("temperature"));
        request.setStats(List.of("avg"));
        request.setStartDate("20260310");
        request.setEndDate("20260301");

        assertThrows(BadRequestException.class, () -> QueryRequestValidator.validate(request));
    }
}