package com.dk.faculty.graphqlaggregator.beans;

import com.dk.faculty.graphqlaggregator.entities.Error;
import com.dk.faculty.graphqlaggregator.entities.Professor;
import com.dk.faculty.graphqlaggregator.entities.Student;
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
public class UserBean {
    private Client httpClient;
    private String baseUrlUsers;

    @Inject
    DiscoveryUtil discoveryUtil;

    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
        baseUrlUsers = discoveryUtil.getServiceInstance("users", "1.0.0", "dev").get().toString() + "/v1";
    }

    public List<Student> getStudents() {
        Response r = httpClient
                .target(baseUrlUsers)
                .path("students")
                .request()
                .get();
        if (r.getStatusInfo().getStatusCode() == Response.Status.OK.getStatusCode()) {
            return r.readEntity(new GenericType<List<Student>>(){});
        } else {
            Error e = r.readEntity(new GenericType<Error>(){});
            throw new GraphQLException(e.getDescription() + " " + e.getLocation());
        }
    }

    public Student getStudent(Integer id) {
        Response r = httpClient
                .target(baseUrlUsers)
                .path("students")
                .path("{id}")
                .resolveTemplate("id", id)
                .request()
                .get();
        if (r.getStatusInfo().getStatusCode() == Response.Status.OK.getStatusCode()) {
            return r.readEntity(new GenericType<Student>(){});
        } else {
            Error e = r.readEntity(new GenericType<Error>(){});
            throw new GraphQLException(e.getDescription() + " " + e.getLocation());
        }
    }

    public List<Professor> getProfessors() {
        Response r = httpClient
                .target(baseUrlUsers)
                .path("professors")
                .request()
                .get();
        if (r.getStatusInfo().getStatusCode() == Response.Status.OK.getStatusCode()) {
            return r.readEntity(new GenericType<List<Professor>>(){});
        } else {
            Error e = r.readEntity(new GenericType<Error>(){});
            throw new GraphQLException(e.getDescription() + " " + e.getLocation());
        }
    }

    public Professor getProfessor(Integer id) {
        Response r = httpClient
                .target(baseUrlUsers)
                .path("professors")
                .path("{id}")
                .resolveTemplate("id", id)
                .request()
                .get();
        if (r.getStatusInfo().getStatusCode() == Response.Status.OK.getStatusCode()) {
            return r.readEntity(new GenericType<Professor>(){});
        } else {
            Error e = r.readEntity(new GenericType<Error>(){});
            throw new GraphQLException(e.getDescription() + " " + e.getLocation());
        }
    }
}
