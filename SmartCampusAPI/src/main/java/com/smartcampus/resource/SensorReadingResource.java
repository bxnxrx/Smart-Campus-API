package com.smartcampus.resource;

import com.smartcampus.model.Sensor;
import com.smartcampus.model.SensorReading;
import com.smartcampus.service.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Path("/sensors/{sensorId}/readings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {

    // ADD reading
    @POST
    public Response addReading(
            @PathParam("sensorId") String sensorId,
            SensorReading reading) {

        Sensor sensor = DataStore.sensors.get(sensorId);

        if (sensor == null) {
            return Response.status(404).entity("Sensor not found").build();
        }

        if (reading == null) {
            return Response.status(400).entity("Invalid reading").build();
        }

        // Auto ID
        reading.setId(UUID.randomUUID().toString());

        // Auto timestamp
        reading.setTimestamp(System.currentTimeMillis());

        DataStore.readings
                .computeIfAbsent(sensorId, k -> new ArrayList<>())
                .add(reading);

        return Response.status(Response.Status.CREATED).entity(reading).build();
    }

    // GET readings (with pagination 🔥)
    @GET
    public Response getReadings(
            @PathParam("sensorId") String sensorId,
            @QueryParam("limit") @DefaultValue("10") int limit,
            @QueryParam("offset") @DefaultValue("0") int offset
    ) {

        Sensor sensor = DataStore.sensors.get(sensorId);

        if (sensor == null) {
            return Response.status(404).entity("Sensor not found").build();
        }

        List<SensorReading> list =
                DataStore.readings.getOrDefault(sensorId, new ArrayList<>());

        List<SensorReading> paginated = list.stream()
                .skip(offset)
                .limit(limit)
                .toList();

        return Response.ok(paginated).build();
    }

    // DELETE reading (🔥 FIXED LOCATION)
    @DELETE
    @Path("/{readingId}")
    public Response deleteReading(
            @PathParam("sensorId") String sensorId,
            @PathParam("readingId") String readingId) {

        Sensor sensor = DataStore.sensors.get(sensorId);

        if (sensor == null) {
            return Response.status(404).entity("Sensor not found").build();
        }

        List<SensorReading> list = DataStore.readings.get(sensorId);

        if (list == null) {
            return Response.status(404).entity("No readings found").build();
        }

        boolean removed = list.removeIf(r -> readingId.equals(r.getId()));

        if (!removed) {
            return Response.status(404).entity("Reading not found").build();
        }

        return Response.ok("Reading deleted").build();
    }
}