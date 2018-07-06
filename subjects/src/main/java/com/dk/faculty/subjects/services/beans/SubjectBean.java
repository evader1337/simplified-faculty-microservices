package com.dk.faculty.subjects.services.beans;

import com.dk.faculty.subjects.entities.Subject;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class SubjectBean {
    @PersistenceContext(unitName = "subjects-jpa")
    private EntityManager em;

    public List<Subject> getSubjects() {
        return em.createQuery("Select s from Subject s").getResultList();
    }

    public Subject getSubject(Integer id) {
        return em.find(Subject.class, id);
    }

    @Transactional
    public Subject addSubject(Subject subject) {
        //preveri če obstaja prostor in profesor
        if(subject != null) {
            em.persist(subject);
            return subject;
        } else {
            return null;
        }
    }

    @Transactional
    public boolean deleteSubject(Integer id) {
        Subject s = getSubject(id);
        if(s != null) {
            em.remove(s);
            return true;
        }
        return false;
    }

    @Transactional
    public Subject updateSubject(Integer id, Subject subject) {
        //preveri če obstaja prostor in profesor
        Subject s = getSubject(id);
        subject.setID(s.getID());
        em.merge(subject);
        return subject;
    }
}
