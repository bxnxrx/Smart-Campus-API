package com.smartcampus.resource;

import com.smartcampus.model.Room;
import com.smartcampus.model.Sensor;
import com.smartcampus.service.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource {

    // GET all rooms
    @GET
    public Response getAllRooms() {
        return Response.ok(DataStore.rooms.values()).build();
    }

    // CREATE room
    @POST
    public Response createRoom(Room room) {

        if (room == null || room.getId() == null || room.getId().isEmpty()) {
            return Response.status(400).entity("Room ID is required").build();
        }

        DataStore.rooms.put(room.getId(), room);
        return Response.status(Response.Status.CREATED).entity(room).build();
    }

    // GET room by ID
    @GET
    @Path("/{id}")
    public Response getRoom(@PathParam("id") String id) {

        Room room = DataStore.rooms.get(id);

        if (room == null) {
            return Response.status(404).entity("Room not found").build();
        }

        return Response.ok(room).build();
    }

    // DELETE room (with safety rule 🔥)
    @DELETE
    @Path("/{id}")
    public Response deleteRoom(@PathParam("id") String id) {

        Room room = DataStore.rooms.get(id);

        if (room == null) {
            return Response.status(404).entity("Room not found").build();
        }

        // Prevent deleting room with sensors
        if (!room.getSensorIds().isEmpty()) {
            return Response.status(400).entity("Cannot delete room with sensors").build();
        }

        DataStore.rooms.remove(id);
        return Response.ok("Room deleted").build();
    }

    // GET sensors inside a room
    @GET
    @Path("/{id}/sensors")
    public Response getSensorsByRoom(@PathParam("id") String id) {

        Room room = DataStore.rooms.get(id);

        if (room == null) {
            return Response.status(404).entity("Room not found").build();
        }

        List<Sensor> sensors = room.getSensorIds().stream()
                .map(DataStore.sensors::get)
                .toList();

        return Response.ok(sensors).build();
    }
}