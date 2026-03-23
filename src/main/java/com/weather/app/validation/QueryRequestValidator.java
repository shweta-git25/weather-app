package com.weather.app.validation;

import com.weather.app.dto.QueryRequest;
import com.weather.app.exception.BadRequestException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class QueryRequestValidator {

    private static final List<String> ALLOWED_STATS = List.of("min", "max", "avg", "sum");

    private static final List<String> ALLOWED_METRICS = List.of("temperature", "humidity", "windspeed");

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static void validate(QueryRequest request) {

        if (request == null) {
            throw new BadRequestException("Request body cannot be null");
        }

        // Validate Sensor Names
        if(request.getSensorNames() != null && request.getSensorNames().isEmpty())
            throw new BadRequestException("Sensor Names can not be empty. Add \"all\" to get All Sensors Data.");

        // Validate metrics
        if (request.getMetrics() == null || request.getMetrics().isEmpty()) {
            throw new BadRequestException("Metrics are required");
        }

        for (String metric : request.getMetrics()) {
            if (!ALLOWED_METRICS.contains(metric.toLowerCase())) {
                throw new BadRequestException("Invalid metric: " + metric + ". Only Allowed Metrics: temperature, humidity, windSpeed");
            }
        }

        // Validate stats
        if (request.getStats() == null || request.getStats().isEmpty()) {
            throw new BadRequestException("Stats are required");
        }

        for (String stat : request.getStats()) {
            if (!ALLOWED_STATS.contains(stat.toLowerCase())) {
                throw new BadRequestException("Invalid Statistics Requested: " + stat + ". Allowed: min, max, avg, sum");
            }
        }

        // Date validation
        if (request.getStartDate() != null && request.getEndDate() != null) {

            LocalDate start = parseDate(request.getStartDate());
            LocalDate end = parseDate(request.getEndDate());

            if (start.isAfter(end)) {
                throw new BadRequestException("Start date cannot be after end date");
            }

            long daysBetween = Duration.between(start.atStartOfDay(), end.atStartOfDay()).toDays();

            // Check Month Validation
            if (daysBetween > 31) {
                throw new BadRequestException("Date range cannot exceed 31 days");
            }
        }


    }

    private static LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date, FORMATTER);
        } catch (DateTimeParseException ex) {
            throw new BadRequestException("Invalid date format. Use yyyyMMdd");
        }
    }
}
