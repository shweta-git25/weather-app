package com.weather.app.repository.impl;

import com.weather.app.dto.QueryRequest;
import com.weather.app.exception.BadRequestException;
import com.weather.app.repository.SensorDataCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SensorDataCustomRepositoryImpl implements SensorDataCustomRepository {

    private final EntityManager entityManager;

    @Override
    public List<Object[]> getAggregatedSensorData(QueryRequest request) {

        StringBuilder sql = new StringBuilder();

        // SELECT Clause
        sql.append("SELECT sensor_name");

        for (String metric : request.getMetrics()) {
            for (String stat : request.getStats()) {
                sql.append(", ")
                        .append(stat.toUpperCase())
                        .append("(")
                        .append(getColumn(metric))
                        .append(") AS ")
                        .append(metric).append("_").append(stat);
            }
        }

        // FROM Clause
        sql.append(" FROM tb_sensor_data WHERE 1=1 ");

        // SENSOR FILTER
        if (request.getSensorNames() != null && !request.getSensorNames().isEmpty() &&  request.getSensorNames().stream().noneMatch("all"::equalsIgnoreCase)) {
            sql.append(" AND sensor_name IN (:sensorNames) ");
        }

        // DATE FILTER
        if (request.getStartDate() != null && request.getEndDate() != null) {

            sql.append(" AND timestamp BETWEEN :start AND :end ");

        }

        // GROUP BY Clause
        sql.append(" GROUP BY sensor_name");

        Query query = entityManager.createNativeQuery(sql.toString());

        // Parameters defined
        if (request.getSensorNames() != null && !request.getSensorNames().isEmpty() && request.getSensorNames().stream().noneMatch("all"::equalsIgnoreCase)) {
            query.setParameter("sensorNames", request.getSensorNames());
        }

        if (request.getStartDate() != null && request.getEndDate() != null) {
            query.setParameter("start", request.getStartDate() + " 00:00:00");
            query.setParameter("end", request.getEndDate() + " 23:59:59");
        }

        return query.getResultList();
    }

    private String getColumn(String metric) {
        return switch (metric.toLowerCase()) {
            case "temperature" -> "temperature";
            case "humidity" -> "humidity";
            case "windspeed" -> "wind_speed";
            default -> throw new BadRequestException("Invalid metric: " + metric);
        };
    }
}