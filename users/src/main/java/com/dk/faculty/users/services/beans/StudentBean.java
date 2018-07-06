package com.dk.faculty.users.services.beans;

import com.dk.faculty.users.entities.Student;
import com.dk.faculty.users.entities.Subject;
import com.kumuluz.ee.discovery.utils.DiscoveryUtil;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class StudentBean {
    @PersistenceContext(unitName = "users-jpa")
    private EntityManager em;
    private Client httpClient;
    private String baseUrlSubjects;

    @Inject
    DiscoveryUtil discoveryUtil;

    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
        baseUrlSubjects = discoveryUtil.getServiceInstance("subjects", "1.0.0", "dev").get().toString() + "/v1";
    }

    public boolean checkForSubject(Integer subjectId) {
        if(subjectId != null) {
            Response r =  httpClient
                    .target(baseUrlSubjects).path("subjects").path("{id}").resolveTemplate("id", subjectId)
                    .request().get();
            if(r.getStatusInfo().getStatusCode() == 404) {
                return false;
            }
        }
        return true;
    }

    public List<Student> getStudents() {
        return em.createQuery("Select s from Student s").getResultList();
    }

    public Student getStudent(Integer id) {
        return em.find(Student.class, id);
    }

    public List<Integer> getStudentSubjects(Integer subjectId) {
        if(!checkForSubject(subjectId)) return null;
        List<Student> students = getStudents();
        List<Integer> subjectStudents = new ArrayList<Integer>();
        for(Student s: students) {
            if(s.hasSubject(subjectId)) {
                subjectStudents.add(s.getID());
            }
        }
        return subjectStudents;
    }

    @Transactional
    public Student addStudent(Student student) {
        if(student != null) {
            em.persist(student);
            return student;
        } else {
            return null;
        }
    }

    @Transactional
    public boolean deleteStudent(Integer id) {
        Student s = getStudent(id);
        if(s != null) {
            em.remove(s);
            return true;
        }
        return false;
    }

    @Transactional
    public Student updateStudent(Integer id, Student student) {
        Student s = getStudent(id);
        student.setID(s.getID());
        em.merge(student);
        return student;
    }

    @Transactional
    public Student addSubject(Integer id, Subject subject) {
        if(!checkForSubject(subject.getSubjectId())) return null;
        Student s = getStudent(id);
        if(s != null) {
            s.addSubject(subject.getSubjectId());
            em.merge(s);
            return s;
        }
        return null;
    }

    @Transactional
    public Student removeSubject(Integer id, Integer subject) {
        Student s = getStudent(id);
        if(s != null) {
            s.removeSubject(subject);
            em.merge(s);
            return s;
        }
        return null;
    }
}
