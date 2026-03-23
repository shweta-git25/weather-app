package com.weather.app.controller;

import com.weather.app.dto.QueryRequest;
import com.weather.app.dto.QueryResponse;
import com.weather.app.dto.SensorDataRequest;
import com.weather.app.entity.SensorData;
import com.weather.app.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {

    @Autowired
    private SensorService sensorService;


    @PostMapping("/insertData")
    public ResponseEntity<String> insertData(@RequestBody List<SensorDataRequest> requests) {

        sensorService.saveAllSensorData(requests);
        return ResponseEntity.ok("{\n\tSensor data inserted successfully\n}");
    }

    @PostMapping("/querySensorData")
    public ResponseEntity<QueryResponse> querySensorData( @RequestBody QueryRequest request) {

        QueryResponse response = sensorService.querySensorData(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SensorData>> getAllSensorData(){

        return ResponseEntity.ok(sensorService.getAllSensorData());
    }

     @GetMapping("/name/{sensorName}")
    public ResponseEntity<List<SensorData>> getSensorByName(@PathVariable String sensorName) {

        List<SensorData> dataList = sensorService.getSensorsByName(sensorName);
        return ResponseEntity.ok(dataList);
     }


}
