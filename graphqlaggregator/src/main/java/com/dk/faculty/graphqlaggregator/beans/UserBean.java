package com.dk.faculty.graphqlaggregator.beans;

import com.dk.faculty.graphqlaggregator.entities.Professor;
import com.dk.faculty.graphqlaggregator.entities.Student;
import com.kumuluz.ee.discovery.utils.DiscoveryUtil;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
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
        return httpClient
                .target(baseUrlUsers)
                .path("students")
                .request()
                .get(new GenericType<List<Student>>(){});
    }

    public Student getStudent(Integer id) {
        return httpClient
                .target(baseUrlUsers)
                .path("students")
                .path("{id}")
                .resolveTemplate("id", id)
                .request()
                .get(new GenericType<Student>(){});
    }

    public List<Professor> getProfessors() {
        return httpClient
                .target(baseUrlUsers)
                .path("professors")
                .request()
                .get(new GenericType<List<Professor>>(){});
    }

    public Professor getProfessor(Integer id) {
        return httpClient
                .target(baseUrlUsers)
                .path("professors")
                .path("{id}")
                .resolveTemplate("id", id)
                .request()
                .get(new GenericType<Professor>(){});
    }
}
