# 🏫 Smart Campus API

A RESTful API built using **Java (JAX-RS - Jersey)** and deployed on **Apache Tomcat**.
This project demonstrates core REST principles including resource management, validation, filtering, sub-resources, and proper HTTP status handling.

---

## 🚀 Features

* ✅ Room Management (Create, Read, Delete)
* ✅ Sensor Management with Room linking
* ✅ Sensor Readings (Sub-resource)
* ✅ Filtering using Query Parameters
* ✅ Pagination support (limit & offset)
* ✅ Validation and Error Handling
* ✅ Discovery Endpoint (`/api/v1`)
* ❌ In-memory storage (no database)

---

## 🧠 Architecture

The project follows a simple layered architecture:

* **Resource Layer** → Handles API endpoints
* **Model Layer** → Represents entities (Room, Sensor, SensorReading)
* **Service Layer** → DataStore using HashMaps
* **Configuration Layer** → ApplicationConfig

---

## 🌐 Base URL

```
http://localhost:8081/SmartCampusAPI/api/v1
```

---

## 🔍 Discovery Endpoint

### Request

```
GET /api/v1
```

### Response

```json
{
  "apiName": "Smart Campus API",
  "version": "v1",
  "studentName": "YOUR_NAME",
  "studentId": "YOUR_ID",
  "endpoints": {
    "rooms": "/api/v1/rooms",
    "sensors": "/api/v1/sensors",
    "readings": "/api/v1/sensors/{sensorId}/readings"
  }
}
```

---

## 🏢 Room Endpoints

### Create Room

```
POST /rooms
```

```json
{
  "id": "R1",
  "name": "Lab",
  "capacity": 50
}
```

### Get All Rooms

```
GET /rooms
```

### Get Room by ID

```
GET /rooms/{id}
```

### Delete Room

```
DELETE /rooms/{id}
```

> ⚠️ Cannot delete a room if it contains sensors

---

## 🔌 Sensor Endpoints

### Create Sensor

```
POST /sensors
```

```json
{
  "id": "S1",
  "type": "temperature",
  "status": "active",
  "currentValue": 25.5,
  "roomId": "R1"
}
```

### Get Sensors

```
GET /sensors
```

### Filter Sensors

```
GET /sensors?type=temperature
GET /sensors?status=active
```

### Delete Sensor

```
DELETE /sensors/{id}
```

---

## 🔥 Sensor Readings (Sub-Resource)

### Add Reading

```
POST /sensors/{sensorId}/readings
```

```json
{
  "value": 27.5
}
```

> ✔ ID and timestamp are auto-generated

---

### Get Readings

```
GET /sensors/{sensorId}/readings
```

### Pagination

```
GET /sensors/{sensorId}/readings?limit=2&offset=0
```

---

### Delete Reading

```
DELETE /sensors/{sensorId}/readings/{readingId}
```

---

## ⚠️ Error Handling

| Status Code | Meaning                |
| ----------- | ---------------------- |
| 200         | Success                |
| 201         | Created                |
| 400         | Bad Request            |
| 404         | Not Found              |
| 415         | Unsupported Media Type |
| 500         | Server Error           |

---

## 🧪 Testing

All endpoints were tested using **Postman**.

---

## ⚠️ Important Note

This system uses **in-memory storage**:

* Data is NOT persistent
* Server restart will reset all data

---

## 🔧 Technologies Used

* Java (JDK 17)
* JAX-RS (Jersey)
* Apache Tomcat
* Maven
* Postman

---

## 📌 Future Improvements

* Add database integration (MySQL / MongoDB)
* Implement authentication (JWT)
* Add logging and exception mappers
* Improve API documentation (Swagger)

---

## 👨‍💻 Author

**Name:** S.W.B Prabhashana
**Student ID:** w2120437 / 20221931

---
