package com.dk.faculty.graphqlaggregator.beans;

import com.dk.faculty.graphqlaggregator.entities.Error;
import com.dk.faculty.graphqlaggregator.entities.Place;
import com.kumuluz.ee.discovery.utils.DiscoveryUtil;
import graphql.GraphQLException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.List;

@RequestScoped
public class PlaceBean {
    private Client httpClient;
    private String baseUrlPlaces;

    @Inject
    DiscoveryUtil discoveryUtil;

    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
        baseUrlPlaces = discoveryUtil.getServiceInstance("places", "1.0.0", "dev").get().toString() + "/v1";
    }

    public List<Place> getPlaces() {
        Response r = httpClient
                .target(baseUrlPlaces)
                .path("places")
                .request()
                .get();
        if (r.getStatusInfo().getStatusCode() == Response.Status.OK.getStatusCode()) {
            return r.readEntity(new GenericType<List<Place>>(){});
        } else {
            Error e = r.readEntity(Error.class);
            throw new GraphQLException(e.getDescription() + " " + e.getLocation());
        }
    }

    public Place getPlace(Integer id) {
        Response r = httpClient
                .target(baseUrlPlaces)
                .path("places")
                .path("{id}")
                .resolveTemplate("id", id)
                .request()
                .get();
        if(r.getStatusInfo().getStatusCode() == Response.Status.OK.getStatusCode()) {
            return r.readEntity(Place.class);
        } else {
            Error e = r.readEntity(Error.class);
            throw new GraphQLException(e.getDescription() + " " + e.getLocation());
        }
    }

    public Place addPlace(Place place) {
        Response r = httpClient
                .target(baseUrlPlaces)
                .path("places")
                .request()
                .post(Entity.json(place));
        if(r.getStatusInfo().getStatusCode() == Response.Status.CREATED.getStatusCode()) {
            return r.readEntity(Place.class);
        } else {
            Error e = r.readEntity(Error.class);
            throw new GraphQLException(e.getDescription() + " " + e.getLocation());
        }
    }

    public Place editPlace(Integer id, Place place) {
        Response r = httpClient
                .target(baseUrlPlaces)
                .path("places")
                .path("{id}")
                .resolveTemplate("id", id)
                .request()
                .put(Entity.json(place));
        if(r.getStatusInfo().getStatusCode() == Response.Status.OK.getStatusCode()) {
            return r.readEntity(Place.class);
        } else {
            Error e = r.readEntity(Error.class);
            throw new GraphQLException(e.getDescription() + " " + e.getLocation());
        }
    }

    public boolean deletePlace(Integer id) {
        Response r = httpClient
                .target(baseUrlPlaces)
                .path("places")
                .path("{id}")
                .resolveTemplate("id", id)
                .request()
                .delete();
        if(r.getStatusInfo().getStatusCode() == Response.Status.NO_CONTENT.getStatusCode()) {
            return true;
        } else {
            Error e = r.readEntity(Error.class);
            throw new GraphQLException(e.getDescription() + " " + e.getLocation());
        }
    }
}
