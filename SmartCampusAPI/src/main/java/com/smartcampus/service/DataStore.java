package com.smartcampus.service;

import com.smartcampus.model.*;
import java.util.*;

public class DataStore {

    // Store rooms
    public static Map<String, Room> rooms = new HashMap<>();

    // Store sensors
    public static Map<String, Sensor> sensors = new HashMap<>();

    // Store sensor readings (sensorId → list of readings)
    public static Map<String, List<SensorReading>> readings = new HashMap<>();

}