package com.dk.faculty.graphqlaggregator.entities;

import io.leangen.graphql.annotations.GraphQLIgnore;

public class Professor extends User {
    private Integer cabinetId;

    @GraphQLIgnore
    public Integer getCabinetId() {
        return cabinetId;
    }

    public void setCabinetId(Integer cabinetId) {
        this.cabinetId = cabinetId;
    }


}
