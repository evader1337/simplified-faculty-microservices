package com.dk.faculty.graphqlaggregator.beans;

import com.dk.faculty.graphqlaggregator.entities.Subject;
import com.kumuluz.ee.discovery.utils.DiscoveryUtil;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
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
        return httpClient
                .target(baseUrlSubjects)
                .path("subjects")
                .request()
                .get(new GenericType<List<Subject>>(){});
    }

    public Subject getSubject(Integer id) {
        return  httpClient
                .target(baseUrlSubjects).path("subjects")
                .path("{id}")
                .resolveTemplate("id", id)
                .request().get(new GenericType<Subject>(){});
    }
}
