package com.dk.faculty.places.services.beans;

import com.dk.faculty.places.entities.Place;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class PlaceBean {
    @PersistenceContext(unitName = "places-jpa")
    private EntityManager em;

    public List<Place> getPlaces() {
        return em.createQuery("Select p from Place p").getResultList();
    }

    public Place getPlace(Integer id) {
        return em.find(Place.class, id);
    }

    @Transactional
    public Place addPlace(Place place) {
        if(place != null) {
            em.persist(place);
            return place;
        } else {
            return null;
        }
    }

    @Transactional
    public boolean deletePlace(Integer id) {
        Place p = getPlace(id);
        if(p != null) {
            em.remove(p);
            return true;
        }
        return false;
    }

    @Transactional
    public Place updatePlace(Integer id, Place place) {
        Place p = getPlace(id);
        place.setID(p.getID());
        em.merge(place);
        return place;
    }
}
