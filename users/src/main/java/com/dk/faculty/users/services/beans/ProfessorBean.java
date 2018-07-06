package com.dk.faculty.users.services.beans;

import com.dk.faculty.users.entities.Professor;
import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
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
import java.net.URL;
import java.util.List;

@ApplicationScoped
public class ProfessorBean {
    @PersistenceContext(unitName = "users-jpa")
    private EntityManager em;
    private Client httpClient;
    private String baseUrl;

    @Inject
    DiscoveryUtil discoveryUtil;

    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
        String address = discoveryUtil.getServiceInstance("places", "1.0.0", "dev").get().toString();
        baseUrl = address + "/v1";
    }

    public List<Professor> getProfessors() {
        return em.createQuery("Select p from Professor p").getResultList();
    }

    public Professor getProfessor(Integer id) {
        return em.find(Professor.class, id);
    }

    @Transactional
    public Professor addProfessor(Professor professor) {
        Integer place = professor.getCabinet();
        if(place != null) {
            Response r =  httpClient
                .target(baseUrl).path("places").path("{id}").resolveTemplate("id", place)
                .request().get();
            if(r.getStatusInfo().getStatusCode() == 404) {
                return null;
            }
        }

        if(professor != null) {
            em.persist(professor);
            return professor;
        } else {
            return null;
        }
    }

    @Transactional
    public boolean deleteProfessor(Integer id) {
        Professor p = getProfessor(id);
        if(p != null) {
            em.remove(p);
            return true;
        }
        return false;
    }

    @Transactional
    public Professor updateProfessor(Integer id, Professor professor) {
        Integer place = professor.getCabinet();
        if(place != null) {
            Response r =  httpClient
                    .target(baseUrl).path("places").path("{id}").resolveTemplate("id", place)
                    .request().get();
            if(r.getStatusInfo().getStatusCode() == 404) {
                return null;
            }
        }
        Professor p = getProfessor(id);
        professor.setID(p.getID());
        em.merge(professor);
        return professor;
    }
}
