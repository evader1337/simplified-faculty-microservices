package com.dk.faculty.graphqlaggregator.beans;

import com.dk.faculty.graphqlaggregator.entities.Place;
import com.kumuluz.ee.discovery.utils.DiscoveryUtil;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import java.util.List;

@ApplicationScoped
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
        return httpClient
                .target(baseUrlPlaces)
                .path("places")
                .request().get(new GenericType<List<Place>>(){});
    }

    public Place getPlace(Integer id) {
        return httpClient
                .target(baseUrlPlaces)
                .path("places")
                .path("{id}")
                .resolveTemplate("id", id)
                .request()
                .get(new GenericType<Place>(){});
    }
}
