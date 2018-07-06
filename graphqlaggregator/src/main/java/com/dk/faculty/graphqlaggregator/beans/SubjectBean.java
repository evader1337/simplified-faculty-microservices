package com.dk.faculty.graphqlaggregator.beans;

import com.dk.faculty.graphqlaggregator.entities.Error;
import com.dk.faculty.graphqlaggregator.entities.Place;
import com.dk.faculty.graphqlaggregator.entities.Subject;
import com.kumuluz.ee.discovery.utils.DiscoveryUtil;
import graphql.GraphQLException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class SubjectBean {
    private Client httpClient;
    private String baseUrlSubjects;

    @Inject
    DiscoveryUtil discoveryUtil;

    @Inject
    private PlaceBean placeBean;

    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
        baseUrlSubjects = discoveryUtil.getServiceInstance("subjects", "1.0.0", "dev").get().toString() + "/v1";
    }

    public List<Subject> getSubjects() {
        Response r = httpClient
                .target(baseUrlSubjects)
                .path("subjects")
                .request()
                .get();
        if (r.getStatusInfo().getStatusCode() == Response.Status.OK.getStatusCode()) {
            return r.readEntity(new GenericType<List<Subject>>(){});
        } else {
            Error e = r.readEntity(new GenericType<Error>(){});
            throw new GraphQLException(e.getDescription() + " " + e.getLocation());
        }
    }

    public Subject getSubject(Integer id) {
        Response r = httpClient
                .target(baseUrlSubjects).path("subjects")
                .path("{id}")
                .resolveTemplate("id", id)
                .request().get();
        if (r.getStatusInfo().getStatusCode() == Response.Status.OK.getStatusCode()) {
            return r.readEntity(new GenericType<Subject>(){});
        } else {
            Error e = r.readEntity(new GenericType<Error>(){});
            throw new GraphQLException(e.getDescription() + " " + e.getLocation());
        }
    }
}
