package com.smartcampus.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class DiscoveryResource {

    @GET
    public Map<String, Object> getApiInfo() {

        Map<String, Object> response = new HashMap<>();

        // Basic API info
        response.put("apiName", "Smart Campus API");
        response.put("version", "v1");

        // 🔥 YOUR DETAILS 
        response.put("studentName", "S.W.B Prabhashana");
        response.put("studentId", "W 2120437 / 20221931");

        // Contact info
        Map<String, String> contact = new HashMap<>();
        contact.put("email", "binara.20221931@iit.ac.lk");

        response.put("contact", contact);

        // Endpoints 
        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("rooms", "/api/v1/rooms");
        endpoints.put("sensors", "/api/v1/sensors");
        endpoints.put("readings", "/api/v1/sensors/{sensorId}/readings");

        response.put("endpoints", endpoints);

        // Optional 
        response.put("status", "running");
        response.put("timestamp", System.currentTimeMillis());

        return response;
    }
}