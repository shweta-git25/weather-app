DROP TABLE IF EXISTS tb_sensor_data;

CREATE TABLE tb_sensor_data (
    sensor_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'Primary key for sensor data',
    sensor_name VARCHAR(50) NOT NULL COMMENT 'Name of the sensor',
    temperature DECIMAL(5,2) COMMENT 'Temperature in degree Celsius',
    humidity DECIMAL(5,2) COMMENT 'Humidity in %',
    wind_speed DECIMAL(5,2) COMMENT 'Wind speed in km/h',
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
        COMMENT 'Timestamp of the record, auto-updates on change'
);