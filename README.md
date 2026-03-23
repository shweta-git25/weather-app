# Weather Sensor Data API

##  Overview

This project is a RESTful backend service that ingests and queries weather sensor data such as temperature, humidity, and wind speed.

---

## Tech Stack

* Java 17
* Spring Boot
* Spring Data JPA
* H2 Database (in-memory)
* Maven
* JUnit 5 + Mockito

---

## How to Run

### 1. Clone the repository

```bash
git clone <your-repo-url>
cd weather-app
```

### 2. Build the project

```bash
mvn.cmd clean install
```

### 3. Run the application

```bash
mvnw.cmd spring-boot:run
```

### 4. Access H2 Console

```
http://localhost:8080/h2-console
```

Use:

* JDBC URL: `jdbc:h2:mem:weather`
* Username: `sa`
* Password: *(leave empty)*

---

## APIs

### 1. Insert Sensor Data

**POST** `/api/sensors/insertData`

#### Request:

```json
[
  {
    "sensorName": "sensor-1",
    "temperature": 25.5,
    "humidity": 60,
    "windSpeed": 10.2,
    "timestamp": "2026-03-20T10:00:00"
  }
]
```

---

### 2. Get All Sensors

**GET** `/api/sensors/all`



---

### 3. Get Sensor by Name

**GET** `/api/sensors/name/{sensorName}`

---

### 4. Query Sensor Data

**POST** `/api/sensors/querySensorData`

#### Example Request:

```json
{
  "sensorNames": ["sensor-1"],
  "metrics": ["temperature", "humidity"],
  "stats": ["avg"],
  "startDate": "20260301",
  "endDate": "20260307"
}
```

---

## Implemented Features

* Insert weather data via API
* Query data by:

    * Sensor(s)
    * Metrics
    * Statistics (min, max, sum, avg)
    * Date range 
* Default latest data if date not provided
* Input validation
* Global exception handling
* Unit tests for:

    * Validator
    * Service
    * Controller

---

## Testing

Run tests using:

```bash
mvnw.cmd test
```

---

##  Notes

* This is a proof-of-concept implementation
* Unit tests cover core logic
* No UI is included as per requirements

---

## 📬 Postman Collection

Refer to attached Postman collection for API testing.
