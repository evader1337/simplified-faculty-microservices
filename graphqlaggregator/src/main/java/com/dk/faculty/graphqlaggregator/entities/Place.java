package com.dk.faculty.graphqlaggregator.entities;

import io.leangen.graphql.annotations.GraphQLIgnore;

public class Place {
    private Integer id;
    private String location;
    private PlaceType placeType;

    public Integer getId() {
        return id;
    }

    @GraphQLIgnore
    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public PlaceType getPlaceType() {
        return placeType;
    }

    public void setPlaceType(PlaceType placeType) {
        this.placeType = placeType;
    }
}
