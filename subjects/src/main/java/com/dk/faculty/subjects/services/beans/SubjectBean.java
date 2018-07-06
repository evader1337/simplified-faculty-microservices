package com.dk.faculty.subjects.services.beans;

import com.dk.faculty.subjects.entities.Subject;
import com.kumuluz.ee.discovery.utils.DiscoveryUtil;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class SubjectBean {
    @PersistenceContext(unitName = "subjects-jpa")
    private EntityManager em;
    private Client httpClient;
    private String baseUrlPlaces;
    private String baseUrlUsers;

    @Inject
    DiscoveryUtil discoveryUtil;

    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
        baseUrlPlaces = discoveryUtil.getServiceInstance("places", "1.0.0", "dev").get().toString() + "/v1";
        baseUrlUsers = discoveryUtil.getServiceInstance("users", "1.0.0", "dev").get().toString() + "/v1";
    }

    public List<Subject> getSubjects() {
        List<Subject> subjects = em.createQuery("Select s from Subject s").getResultList();
        for(Subject s: subjects) {
            s.setStudents(httpClient
                    .target(baseUrlUsers).path("student-subjects").path("{id}").resolveTemplate("id", s.getID())
                    .request().get(new GenericType<List<Integer>>() {}));
        }
        return subjects;
    }

    public Subject getSubject(Integer id, boolean withStudents) {
        Subject s =  em.find(Subject.class, id);
        if(s != null && withStudents) {
            s.setStudents(httpClient
                    .target(baseUrlUsers).path("student-subjects").path("{id}").resolveTemplate("id", s.getID())
                    .request().get(new GenericType<List<Integer>>() {}));
        }
        return s;
    }

    public boolean checkForPlacesAndUsers(Subject s) {
        if(s.getPlace() != null) {
            Response r =  httpClient
                    .target(baseUrlPlaces).path("places").path("{id}").resolveTemplate("id", s.getPlace())
                    .request().get();
            if(r.getStatusInfo().getStatusCode() == 404) {
                return false;
            }
        }
        if(s.getProfessor() != null) {
            Response r =  httpClient
                    .target(baseUrlUsers).path("professors").path("{id}").resolveTemplate("id", s.getProfessor())
                    .request().get();
            if(r.getStatusInfo().getStatusCode() == 404) {
                return false;
            }
        }
        return true;
    }

    @Transactional
    public Subject addSubject(Subject subject) {
        if(!checkForPlacesAndUsers(subject)) return null;
        if(subject != null) {
            em.persist(subject);
            return subject;
        } else {
            return null;
        }
    }

    @Transactional
    public boolean deleteSubject(Integer id) {
        Subject s = getSubject(id, false);
        if(s != null) {
            em.remove(s);
            return true;
        }
        return false;
    }

    @Transactional
    public Subject updateSubject(Integer id, Subject subject) {
        if(!checkForPlacesAndUsers(subject)) return null;
        Subject s = getSubject(id, false);
        subject.setID(s.getID());
        em.merge(subject);
        return subject;
    }
}
