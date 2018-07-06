package com.dk.faculty.places.api.v1.resources;

import com.dk.faculty.places.entities.Place;
import com.dk.faculty.places.services.beans.PlaceBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("places")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlaceResource {
    @Inject
    private PlaceBean placeBean;

    @GET
    public Response getPlaces() {
        List<Place> places = placeBean.getPlaces();
        if(places != null) {
            return Response.status(Response.Status.OK).entity(places).build();
        }
        throw new RuntimeException("Failed getting places.");
    }

    @Path("{id}")
    @GET
    public Response getPlace(@PathParam("id") Integer id) {
        Place place = placeBean.getPlace(id);
        if(place != null) {
            return Response.status(Response.Status.OK).entity(place).build();
        }
        throw new NotFoundException("Place with id " + id + " not found in database.");
    }

    @POST
    public Response addPlace(Place place) {
        Place p = placeBean.addPlace(place);
        if(p != null) {
            return Response.status(Response.Status.CREATED).entity(p).build();
        }
        throw new BadRequestException("Provided invalid fields. Please check your input.");
    }

    @Path("{id}")
    @DELETE
    public Response deletePlace(@PathParam("id") Integer id) {
        if(placeBean.deletePlace(id)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        throw new NotFoundException("Place with id " + id + " not found in database.");
    }

    @Path("{id}")
    @PUT
    public Response updatePlace(@PathParam("id") Integer id, Place place) {
        Place p = placeBean.updatePlace(id, place);
        if(p != null) {
            return Response.status(Response.Status.OK).entity(p).build();
        }
        throw new NotFoundException("Place with id " + id + " not found in database.");
    }
}
