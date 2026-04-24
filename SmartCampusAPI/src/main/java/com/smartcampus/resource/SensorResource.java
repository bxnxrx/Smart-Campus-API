package com.smartcampus.resource;

import com.smartcampus.model.Room;
import com.smartcampus.model.Sensor;
import com.smartcampus.service.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.stream.Collectors;

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {

    // CREATE sensor
    @POST
    public Response createSensor(Sensor sensor) {

        if (sensor == null || sensor.getId() == null || sensor.getRoomId() == null) {
            return Response.status(400).entity("Invalid sensor data").build();
        }

        // Check room exists
        Room room = DataStore.rooms.get(sensor.getRoomId());
        if (room == null) {
            return Response.status(404).entity("Room not found").build();
        }

        DataStore.sensors.put(sensor.getId(), sensor);

        // Link sensor to room
        room.getSensorIds().add(sensor.getId());

        return Response.status(Response.Status.CREATED).entity(sensor).build();
    }

    // GET sensors (with filtering 🔥)
    @GET
    public Response getSensors(
            @QueryParam("type") String type,
            @QueryParam("status") String status
    ) {

        List<Sensor> result = new ArrayList<>(DataStore.sensors.values());

        if (type != null) {
            result = result.stream()
                    .filter(s -> s.getType().equalsIgnoreCase(type))
                    .collect(Collectors.toList());
        }

        if (status != null) {
            result = result.stream()
                    .filter(s -> s.getStatus().equalsIgnoreCase(status))
                    .collect(Collectors.toList());
        }

        return Response.ok(result).build();
    }

    // GET sensor by ID
    @GET
    @Path("/{id}")
    public Response getSensor(@PathParam("id") String id) {

        Sensor sensor = DataStore.sensors.get(id);

        if (sensor == null) {
            return Response.status(404).entity("Sensor not found").build();
        }

        return Response.ok(sensor).build();
    }

    // DELETE sensor
    @DELETE
    @Path("/{id}")
    public Response deleteSensor(@PathParam("id") String id) {

        Sensor sensor = DataStore.sensors.get(id);

        if (sensor == null) {
            return Response.status(404).entity("Sensor not found").build();
        }

        // Remove from room
        Room room = DataStore.rooms.get(sensor.getRoomId());
        if (room != null) {
            room.getSensorIds().remove(id);
        }

        DataStore.sensors.remove(id);

        return Response.ok("Sensor deleted").build();
    }
}